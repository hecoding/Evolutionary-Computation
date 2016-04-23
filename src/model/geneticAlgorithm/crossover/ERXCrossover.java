package model.geneticAlgorithm.crossover;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;
import util.RandGenerator;

public class ERXCrossover implements CrossoverInterface {

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
		RandGenerator random = RandGenerator.getInstance();
		int currentGeneLength = population.get(0).getLength();
		
		ArrayList<AbstractGene<?>> parent1Gene = (ArrayList<AbstractGene<?>>) parent1.getGenotype();
		ArrayList<AbstractGene<?>> parent2Gene = (ArrayList<AbstractGene<?>>) parent2.getGenotype();
		ArrayList<AbstractGene<?>> child1Gene = new ArrayList<AbstractGene<?>>(currentGeneLength);
		ArrayList<AbstractGene<?>> child2Gene = new ArrayList<AbstractGene<?>>(currentGeneLength);
		
		/* De entre todas las ciudades se toma la menos conectada. Como el grafo del problema
		 * es completo, se toma al azar la siguiente ciudad. Para todas.
		 * Por el mismo motivo, tampoco se va a producir bloqueo. */
		
		child1Gene.add(parent1Gene.get(0).clone());
		
		for (int i = 1; i < parent1Gene.size(); i++) {
			int idx = random.nextInt(parent1Gene.size());
			if(!child1Gene.contains(parent1Gene.get(idx)))
				child1Gene.add(parent1Gene.get(idx).clone());
			else
				i--;
		}
		
		child2Gene.add(parent2Gene.get(0).clone());
		
		for (int i = 1; i < parent2Gene.size(); i++) {
			int idx = random.nextInt(parent2Gene.size());
			if(!child2Gene.contains(parent2Gene.get(idx)))
				child2Gene.add(parent2Gene.get(idx).clone());
			else
				i--;
		}
		
		child1.setGenotype(child1Gene);
		child2.setGenotype(child2Gene);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public String getName() {
		return "ERX";
	}

}
