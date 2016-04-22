package model.geneticAlgorithm.crossover;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;
import model.gene.IntegerGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import util.RandGenerator;

public class CXCrossover implements CrossoverInterface {

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
		
		// select two points over 0 and the current gene length - 1
		int point1 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		int point2 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		if (point1 > point2) {
			int temp = point1;
			point1 = point2;
			point2 = temp;
		}
		
		// initialize genes with not valid items so that the .set doesn't blows up
		for (int i = 0; i < parent1Gene.size(); i++) {
			child1Gene.add(new IntegerGene(-1));
			child2Gene.add(new IntegerGene(-1));
		}
		
		// copy cyclically
		AbstractGene<?> firstElem = parent1Gene.get(0);
		int i = 0;
		do {
			child1Gene.set(i, parent1Gene.get(i).clone());
			AbstractGene<?> c = parent2Gene.get(i);
			i = parent1Gene.indexOf(c);
		} while(!parent2Gene.get(i).equals(firstElem));
		
		// copy the remaining genes of the second parent
		i = 0;
		int ii = 0, idx = -1;
		IntegerGene a = new IntegerGene(-1);
		i = child1Gene.indexOf(a);
		while(i != -1) {
			while(ii != -1) {
				idx++;
				ii = child1Gene.indexOf(parent2Gene.get(idx));
			}
			ii = 0;
			child1Gene.set(i, parent2Gene.get(idx).clone());
			i = child1Gene.indexOf(a);
		}
		
		// same for the second child
		firstElem = parent2Gene.get(0);
		i = 0;
		do {
			child2Gene.set(i, parent2Gene.get(i).clone());
			AbstractGene<?> c = parent1Gene.get(i);
			i = parent2Gene.indexOf(c);
		} while(!parent1Gene.get(i).equals(firstElem));
		
		// copy the remaining genes of the first parent
		i = 0;
		ii = 0; idx = -1;
		i = child2Gene.indexOf(a);
		while(i != -1) {
			while(ii != -1) {
				idx++;
				ii = child2Gene.indexOf(parent1Gene.get(idx));
			}
			ii = 0;
			child2Gene.set(i, parent1Gene.get(idx).clone());
			i = child2Gene.indexOf(a);
		}
		
		
		child1.setGenotype(child1Gene);
		child2.setGenotype(child2Gene);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public String getName() {
		return "CX";
	}

}
