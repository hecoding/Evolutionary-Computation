package model.genProgAlgorithm.mutation;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.chromosome.AntTrailChromosome;
import model.program.Function;
import model.program.Node;
import util.RandGenerator;
import util.Tree;

public class SimpleFunctionalMutation implements MutationInterface {
	@Override
	public <T extends AbstractChromosome> void mutate(ArrayList<T> population, double mutationProb) {
		RandGenerator random = RandGenerator.getInstance();
		
		for (T chrom : population) {
			if(random.nextDouble() < mutationProb) {
				mutate(chrom, mutationProb);
				chrom.setAptitude(chrom.evaluate());
			}
		}
	}
	
	public <T extends AbstractChromosome> void mutate(T chrom, double mutationProb) {
		RandGenerator random = RandGenerator.getInstance();
		boolean finish = false;
		Tree<Node> inner;

		// progn3 is the only 3-children function so it can't mutate into another with same number of children
		do {
			inner = ((AntTrailChromosome) chrom).getProgram().getRandomInnerNode();
		} while (inner.getValue().equals(Function.progn3));
		
		Function innerFunction = (Function) inner.getValue();
		Function newFunction;
		do {
			newFunction = Function.values()[random.nextInt(Function.values().length)];
			if (!innerFunction.equals(newFunction) && Function.numberOfChildren(innerFunction) == Function.numberOfChildren(newFunction)) {
				inner.setValue(newFunction);
				finish = true;
			}
		} while(!finish);
		
	}

	@Override
	public String getName() {
		return "Funcional smp.";
	}
}
