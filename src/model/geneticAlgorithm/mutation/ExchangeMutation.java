package model.geneticAlgorithm.mutation;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import util.RandGenerator;

public class ExchangeMutation implements MutationInterface {

	@Override
	public <T extends AbstractChromosome<?>> void mutate(ArrayList<T> population, double mutationProb) {
		RandGenerator random = RandGenerator.getInstance();
		
		for (T chrom : population) {
			if(random.nextDouble() < mutationProb) {
				mutate(chrom, mutationProb);
				chrom.setAptitude(chrom.evaluate());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractChromosome<?>> void mutate(T chrom, double mutationProb) {
		RandGenerator random = RandGenerator.getInstance();
		ArrayList<AbstractGene<?>> genes = (ArrayList<AbstractGene<?>>) chrom.getGenotype();
		
		// select two points over 0 and the current gene length - 1
		int point1 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		int point2 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		
		AbstractGene<?> temp = genes.get(point1);
		genes.set(point1, genes.get(point2));
		genes.set(point2, temp);
		
	}

	@Override
	public String getName() {
		return "Exchange";
	}

}
