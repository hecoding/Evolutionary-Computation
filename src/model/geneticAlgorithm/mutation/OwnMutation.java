package model.geneticAlgorithm.mutation;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import util.RandGenerator;

public class OwnMutation implements MutationInterface {
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
		
		// select a point from the first to the penultimate
		int point1 = random.nextInt(TSPGeneticAlgorithm.Cities.number - 1);
		
		// look for the closest next point
		int closestIdx = 0;
		int closest = Integer.MAX_VALUE;
		for (int i = 0; i < genes.size(); i++) {
			if(i != point1) { // if not the same (distance = 0)
				int distance = TSPGeneticAlgorithm.Cities.distance(point1, i);
				if(distance < closest) {
					closestIdx = i;
					closest = distance;
				}
			}
		}
		
		// exchange closest to sit it next to point1
		AbstractGene<?> temp = genes.get(point1 + 1);
		genes.set(point1 + 1, genes.get(closestIdx));
		genes.set(closestIdx, temp);
		
	}

	@Override
	public String getName() {
		return "Propio";
	}
}
