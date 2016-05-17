package model.genProgAlgorithm.initialization;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.chromosome.AntTrailChromosome;
import model.genProgAlgorithm.fitnessFunction.FitnessFunctionInterface;
import model.program.Function;
import model.program.Node;
import model.program.Terminal;
import util.RandGenerator;
import util.Tree;

public class FullInitialization implements InitializationInterface {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AbstractChromosome> ArrayList<T> initialize(int populationSize, FitnessFunctionInterface function, int programDepth) {
		ArrayList<AntTrailChromosome> population = new ArrayList<AntTrailChromosome>(populationSize);
		for (int i = 0; i < populationSize; i++)  {
			AntTrailChromosome chromosome = new AntTrailChromosome(function);
			Tree<Node> program = new Tree<>(null);
			initialize(program, programDepth);
			
			chromosome.setProgram(program);
			population.add(chromosome);
		}
		
		return (ArrayList<T>) population;
	}
	
	private void initialize(Tree<Node> program, int programDepth) {
		if(programDepth == 1)
			program.setValue(Terminal.values()[RandGenerator.getInstance().nextInt(Terminal.values().length)]);
		
		else {
			program.setValue(Function.values()[RandGenerator.getInstance().nextInt(Function.values().length)]);
			
			for (int i = 0; i < Function.numberOfChildren(program.getValue()); i++) {
				Tree<Node> child = new Tree<>(program);
				initialize(child, programDepth - 1);
				program.addChild(child);
			}
		}
	}

	@Override
	public String getName() {
		return "Completa";
	}

}
