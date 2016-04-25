package model.geneticAlgorithm.crossover;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;
import model.chromosome.TSPChromosome;
import model.gene.AbstractGene;
import util.OrdinalFactory;
import util.RandGenerator;

public class OrdinalCrossover implements CrossoverInterface {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractChromosome<?>> void crossover(ArrayList<T> population, double crossProb) {
		ArrayList<Integer> selectedCandidatesIdx = new ArrayList<Integer>(population.size());
		Random random = RandGenerator.getInstance();
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
			cross(population, random, parent1, parent2, child1, child2);
			
			population.set(selectedCandidatesIdx.get(i), child1);
			population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends AbstractChromosome<?>> void cross(ArrayList<T> population, Random random,
			T parent1, T parent2, T child1, T child2) {
		// transform normal chromosome to ordinal chromosome
		parent1 = (T) OrdinalFactory.ordinalize((TSPChromosome) parent1);
		parent2 = (T) OrdinalFactory.ordinalize((TSPChromosome) parent2);
		
		int currentGeneLength = population.get(0).getLength();
			
		// select point over 0 and the current gene length - 1
		int crossoverPoint = random.nextInt(currentGeneLength);
		
		ArrayList<AbstractGene<?>> parent1Gene = (ArrayList<AbstractGene<?>>) parent1.getGenotype();
		ArrayList<AbstractGene<?>> parent2Gene = (ArrayList<AbstractGene<?>>) parent2.getGenotype();
		ArrayList<AbstractGene<?>> child1Gene = new ArrayList<AbstractGene<?>>(currentGeneLength);
		ArrayList<AbstractGene<?>> child2Gene = new ArrayList<AbstractGene<?>>(currentGeneLength);
		
		// before the crossover point
		for (int j = 0; j < crossoverPoint; j++) {
			child1Gene.add(parent1Gene.get(j));
			child2Gene.add(parent2Gene.get(j));
		}
		
		// after the crossover point
		for (int j = crossoverPoint; j < currentGeneLength; j++) {
			child1Gene.add(parent2Gene.get(j));
			child2Gene.add(parent1Gene.get(j));
		}
		
		child1.setGenotype(child1Gene);
		child2.setGenotype(child2Gene);
		
		// transform ordinal chromosome to normal chromosome
		child1 = (T) OrdinalFactory.deOrdinalize((TSPChromosome) child1);
		child2 = (T) OrdinalFactory.deOrdinalize((TSPChromosome) child2);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public String getName() {
		return "Ordinal";
	}

}