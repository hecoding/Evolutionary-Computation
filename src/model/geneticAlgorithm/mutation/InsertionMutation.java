package model.geneticAlgorithm.mutation;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import util.RandGenerator;

public class InsertionMutation implements MutationInterface {

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
		int numberOfInsertions = 1;
		
		for (int k = 0; k < numberOfInsertions; k++) {
			
			// select two points over 0 and the current gene length - 1
			int point1 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
			int point2 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
			if (point1 > point2) {
				int temp = point1;
				point1 = point2;
				point2 = temp;
			}
			AbstractGene<?> temp = genes.get(point2);
			
			for (int i = point2 - 1; i >= point1; i--) {
				genes.set(i + 1, genes.get(i));
			}
			
			genes.set(point1, temp);
		}
		
	}

	@Override
	public String getName() {
		return "Insertion";
	}

}
