package model.geneticAlgorithm.crossover;

import java.util.ArrayList;
import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;
import model.gene.IntegerGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import util.RandGenerator;

public class PMXCrossover implements CrossoverInterface {

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
		IntegerGene invalidGene = new IntegerGene(-1);
		
		ArrayList<AbstractGene<?>> parent1Gene = (ArrayList<AbstractGene<?>>) parent1.getGenotype();
		ArrayList<AbstractGene<?>> parent2Gene = (ArrayList<AbstractGene<?>>) parent2.getGenotype();
		ArrayList<AbstractGene<?>> child1Gene = new ArrayList<AbstractGene<?>>(currentGeneLength);
		ArrayList<AbstractGene<?>> child2Gene = new ArrayList<AbstractGene<?>>(currentGeneLength);
		
		// select two points over 0 and the current gene length - 1
		int point1 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		int point2 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		
		// fill genes with not valid items so that the .set doesn't blows up
		for (int i = 0; i < parent1Gene.size(); i++) {
			child1Gene.add(new IntegerGene(-1));
			child2Gene.add(new IntegerGene(-1));
		}
		
		// copy the same string between points
		for (int i = point1; i <= point2; i++) {
			child1Gene.set(i, parent2Gene.get(i).clone());
			child2Gene.set(i, parent1Gene.get(i).clone());
		}
		
		// put non-conflictive genes
		for (int i = 0; i < parent1Gene.size(); i++) {
			if(i < point1 || i > point2) {
				if( !child1Gene.contains(parent1Gene.get(i)) )
					child1Gene.set(i, parent1Gene.get(i).clone());
				
				if( !child2Gene.contains(parent2Gene.get(i)) )
					child2Gene.set(i, parent2Gene.get(i).clone());
				
			}
		}
		
		// handle conflictive genes
		int idxRepeatedGene = child1Gene.indexOf(invalidGene); // check if genes to treat
		while (idxRepeatedGene != -1) {
			int idx = idxRepeatedGene;
			AbstractGene<?> partner = null;
			boolean transitiveLookup = false;
			while(idx != -1) {
				AbstractGene<?> repeated = null;
				if(!transitiveLookup)
					repeated = parent1Gene.get(idx);// take the one we can't put in the chromosome
				else
					repeated = parent2Gene.get(idx);
				int idxRepeated = child1Gene.indexOf(repeated); // take its place
				partner = parent1Gene.get(idxRepeated); // take the same place partner
				idx = child1Gene.indexOf(partner); // check if we need to transitively take the next partner
				if(idx != -1)
					transitiveLookup = true;
			}
			
			child1Gene.set(idxRepeatedGene, partner.clone());
			idxRepeatedGene = child1Gene.indexOf(invalidGene);
		}
		
		// same thing with second child
		idxRepeatedGene = child2Gene.indexOf(invalidGene);
		while (idxRepeatedGene != -1) {
			int idx = idxRepeatedGene;
			AbstractGene<?> partner = null;
			boolean transitiveLookup = false;
			while(idx != -1) {
				AbstractGene<?> repeated = null;
				if(!transitiveLookup)
					repeated = parent2Gene.get(idx);
				else
					repeated = parent1Gene.get(idx);
				int idxRepeated = child2Gene.indexOf(repeated);
				partner = parent2Gene.get(idxRepeated);
				idx = child2Gene.indexOf(partner);
				if(idx != -1)
					transitiveLookup = true;
			}
			
			child2Gene.set(idxRepeatedGene, partner.clone());
			idxRepeatedGene = child2Gene.indexOf(invalidGene);
		}
		
		child1.setGenotype(child1Gene);
		child2.setGenotype(child2Gene);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public String getName() {
		return "PMX";
	}

}
