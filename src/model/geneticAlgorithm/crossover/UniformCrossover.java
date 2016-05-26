package model.geneticAlgorithm.crossover;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;

public class UniformCrossover implements CrossoverInterface {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractChromosome<?>> void crossover(ArrayList<T> population, Random random, double crossProb) {
		ArrayList<Integer> selectedCandidatesIdx = new ArrayList<Integer>(population.size());
		T child1 = null, child2 = null;
		
		// select randomly all the candidates for the crossover
		for (int i = 0; i < population.size(); i++) {
			if(random.nextDouble() < crossProb)
				selectedCandidatesIdx.add(i);
		}
		
		// make size even
		if((selectedCandidatesIdx.size() % 2) == 1)
			selectedCandidatesIdx.remove(selectedCandidatesIdx.size() - 1);
		
		// iterate over pairs
		for (int i = 0; i < selectedCandidatesIdx.size(); i+=2) {
			T parent1 = population.get(selectedCandidatesIdx.get(i));
			T parent2 = population.get(selectedCandidatesIdx.get(i + 1));
			child1 = (T) parent1.clone(); child1.setGenotype(new ArrayList<AbstractGene<?>>());
			child2 = (T) parent1.clone(); child2.setGenotype(new ArrayList<AbstractGene<?>>());
			cross(crossProb, random, parent1, parent2, child1, child2);
			
			population.set(selectedCandidatesIdx.get(i), child1);
			population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends AbstractChromosome<?>> void cross(double crossProb, Random random,
			T parent1, T parent2, T child1, T child2) {
		ArrayList<AbstractGene<?>> parent1Gene = (ArrayList<AbstractGene<?>>) parent1.getGenotype();
		ArrayList<AbstractGene<?>> parent2Gene = (ArrayList<AbstractGene<?>>) parent2.getGenotype();
		ArrayList<AbstractGene<?>> child1Gene = new ArrayList<AbstractGene<?>>(parent1Gene.size());
		ArrayList<AbstractGene<?>> child2Gene = new ArrayList<AbstractGene<?>>(parent2Gene.size());
		
		for (int i = 0; i < parent1.getLength(); i++) {
			if(random.nextDouble() > crossProb) {
				child1Gene.add(parent1Gene.get(i).clone());
				child2Gene.add(parent2Gene.get(i).clone());
			}
			else {
				child1Gene.add(parent2Gene.get(i).clone());
				child2Gene.add(parent1Gene.get(i).clone());
			}
		}
		
		child1.setGenotype(child1Gene);
		child2.setGenotype(child2Gene);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public String getName() {
		return "uniform";
	}

}
