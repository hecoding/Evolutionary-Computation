package model.geneticAlgorithm.crossover;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;
import model.chromosome.exception.IllegalChromosomeException;
import model.gene.AbstractGene;
import model.gene.DoubleGene;

public class SimulatedBinaryCrossover implements CrossoverInterface {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractChromosome<?>> void crossover(ArrayList<T> population, Random random, double crossProb) {
		if(!(population.get(0).getGenotype().get(0) instanceof DoubleGene)) {
			throw new IllegalChromosomeException(population.get(0).getGenotype().get(0).getClass(), DoubleGene.class);
		}
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
		ArrayList<AbstractGene<?>> parent1Genes = (ArrayList<AbstractGene<?>>) parent1.getGenotype();
		ArrayList<AbstractGene<?>> parent2Genes = (ArrayList<AbstractGene<?>>) parent2.getGenotype();
		ArrayList<AbstractGene<?>> child1Genes = new ArrayList<AbstractGene<?>>(parent1Genes.size());
		ArrayList<AbstractGene<?>> child2Genes = new ArrayList<AbstractGene<?>>(parent2Genes.size());
		
		double beta = 0;
		int n = 1;
		if(crossProb < 0.5)
			beta = 2 * Math.pow(crossProb, 1/(n + 1));
		else
			beta = Math.pow( 1 / (2 * (1 - crossProb)) , 1 / (n + 1) );
		
		for (int i = 0; i < parent1.getLength(); i++) {
			double p1i = (double) parent1Genes.get(i).getInformation();
			double p2i = (double) parent2Genes.get(i).getInformation();
			
			double inf1 = 0.5 * (p1i + p2i) - beta * (Math.abs(p2i - p1i));
			double inf2 = 0.5 * (p1i + p2i) + beta * (Math.abs(p2i - p1i));
			
			child1Genes.add(new DoubleGene(inf1));
			child2Genes.add(new DoubleGene(inf2));
		}
		
		child1.setGenotype(child1Genes);
		child2.setGenotype(child2Genes);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public String getName() {
		return "SBX";
	}

}
