package model.geneticAlgorithm.crossover;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;
import model.gene.IntegerGene;
import util.RandGenerator;

public class OwnCrossover implements CrossoverInterface {
	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractChromosome<?>> void crossover(ArrayList<T> population, double crossProb) {
		RandGenerator random = RandGenerator.getInstance();
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
			cross(population, parent1, parent2, child1, child2);
			
			population.set(selectedCandidatesIdx.get(i), child1);
			population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends AbstractChromosome<?>> void cross(ArrayList<T> population, T parent1, T parent2, T child1, T child2) {
		// it's basically OX with another kind of selection for fixed elements
		int currentGeneLength = population.get(0).getLength();
		IntegerGene invalidGene = new IntegerGene(-1);
		
		ArrayList<AbstractGene<?>> parent1Gene = (ArrayList<AbstractGene<?>>) parent1.getGenotype();
		ArrayList<AbstractGene<?>> parent2Gene = (ArrayList<AbstractGene<?>>) parent2.getGenotype();
		ArrayList<AbstractGene<?>> child1Gene = new ArrayList<AbstractGene<?>>(currentGeneLength);
		ArrayList<AbstractGene<?>> child2Gene = new ArrayList<AbstractGene<?>>(currentGeneLength);
		ArrayList<Integer> fixedGenes = new ArrayList<Integer>();
		
		int sumBoundary = 20;
		
		// sum each pair of parental genes and take those which are below the boundary
		for (int i = 0; i < parent1Gene.size(); i++) {
			int sum = ((int) parent1Gene.get(i).getInformation()) + ((int)parent2Gene.get(i).getInformation());
			if(sum < sumBoundary)
				fixedGenes.add(i);
		}
		
		// initialize children genes with not valid items so that the .set doesn't blows up
		for (int i = 0; i < parent1Gene.size(); i++) {
			child1Gene.add(new IntegerGene(-1));
			child2Gene.add(new IntegerGene(-1));
		}
		
		// copy fixed genes
		for (Integer idx : fixedGenes) {
			child1Gene.set(idx, parent2Gene.get(idx).clone());
			child2Gene.set(idx, parent1Gene.get(idx).clone());
		}
		
		// copy the rest in order
		int placeholder = child1Gene.indexOf(invalidGene);
		for (int i = 0; placeholder != -1 && i < parent1Gene.size(); i++) {
			if(!child1Gene.contains(parent1Gene.get(i))) {
				child1Gene.set(placeholder, parent1Gene.get(i).clone());
				placeholder = child1Gene.indexOf(invalidGene);
			}
		}
		
		// same for the second child
		placeholder = child2Gene.indexOf(invalidGene);
		for (int i = 0; placeholder != -1 && i < parent2Gene.size(); i++) {
			if(!child2Gene.contains(parent2Gene.get(i))) {
				child2Gene.set(placeholder, parent2Gene.get(i).clone());
				placeholder = child2Gene.indexOf(invalidGene);
			}
		}
		
		child1.setGenotype(child1Gene);
		child2.setGenotype(child2Gene);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public String getName() {
		return "Own";
	}
}
