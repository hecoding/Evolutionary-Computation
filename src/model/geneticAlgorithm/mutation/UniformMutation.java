package model.geneticAlgorithm.mutation;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.chromosome.DoubleChromosome;
import model.gene.DoubleGene;
import util.RandGenerator;

public class UniformMutation implements MutationInterface {

	@Override
	public <T extends AbstractChromosome<?>> void mutate(ArrayList<T> population, double mutationProb) {
		for (T chrom : population) {
			mutate(chrom, mutationProb);
			
			chrom.setAptitude(chrom.evaluate());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractChromosome<?>> void mutate(T chrom, double mutationProb) {
		RandGenerator random = RandGenerator.getInstance();
		ArrayList<DoubleGene> genes = (ArrayList<DoubleGene>) chrom.getGenotype();
		
		for (int i = 0; i < genes.size(); i++) {
			if(random.nextDouble() < mutationProb) {
				genes.get(i).setInformation(function.getLimits().get(i).minx + 
						random.nextDouble() * (function.getLimits().get(i).maxx - function.getLimits().get(i).minx));
			}
		}
	}

	@Override
	public String getName() {
		return "uniforme";
	}

}
