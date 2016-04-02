package model.geneticAlgorithm.crossover;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;
import model.chromosome.BooleanChromosome;
import model.gene.AbstractGene;
import model.gene.BooleanGene;

public class OnepointBitToBitCrossover implements CrossoverInterface {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractChromosome<?>> void crossover(ArrayList<T> population, Random random,
			double crossProb) {
		if(!(population.get(0).getGenotype().get(0) instanceof BooleanGene))
			throw new IllegalChromosomeException("Bad gene, " + population.get(0).getGenotype().get(0).getClass() + " must be BooleanGene");
		
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
			cross(population, random, parent1, parent2, child1, child2);
			
			population.set(selectedCandidatesIdx.get(i), (T) child1);
			population.set(selectedCandidatesIdx.get(i + 1), (T) child2);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends AbstractChromosome> void cross(ArrayList<T> population, Random random,
			T parent1, T parent2, T child1, T child2) {
		int geneNum = ((BooleanChromosome) population.get(0)).getGeneNum();
		for (int i = 0; i < geneNum; i++) {
			int currentGeneLength = ((BooleanGene) population.get(0).getGenotype().get(i)).getLength();
			
			// select point over 0 and the current gene length - 1
			int crossoverPoint = random.nextInt(currentGeneLength);
			
			BooleanGene parent1Gene = (BooleanGene) parent1.getGenotype().get(i);
			BooleanGene parent2Gene = (BooleanGene) parent2.getGenotype().get(i);
			BooleanGene child1Gene = new BooleanGene(currentGeneLength);
			BooleanGene child2Gene = new BooleanGene(currentGeneLength);
			
			// before the crossover point
			for (int j = 0; j < crossoverPoint; j++) {
				child1Gene.add(parent1Gene.getInformation().get(j));
				child2Gene.add(parent2Gene.getInformation().get(j));
			}
			
			// after the crossover point
			for (int j = crossoverPoint; j < currentGeneLength; j++) {
				child1Gene.add(parent2Gene.getInformation().get(j));
				child2Gene.add(parent1Gene.getInformation().get(j));
			}
			
			child1.add(child1Gene);
			child2.add(child2Gene);
		}
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public String getName() {
		return "un punto bit";
	}
	
	public class IllegalChromosomeException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;
		
		public IllegalChromosomeException(String s) {
			super(s);
		}
	}

}
