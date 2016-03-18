package model.geneticAlgorithm;

import java.util.ArrayList;
import model.chromosome.DoubleChromosome;
import model.function.Function;
import model.gene.DoubleGene;

public class DoubleGeneticAlgorithm extends AbstractGeneticAlgorithm<DoubleChromosome> {

	public DoubleGeneticAlgorithm(Function func, int populationNum,
			boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		super(func, populationNum, useElitism, elitePercentage, maxGenerationNum,
				crossProb, mutationProb, tolerance, customSeed, seed);
	}

	public void initialize() {
		this.population.clear();
		
		for (int i = 0; i < this.populationNum; i++) {
			DoubleChromosome chr = new DoubleChromosome(function, this.tolerance, random);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.add(chr);
		}
	}

	@Override
	public void selection() {
		// roulette selection
		ArrayList<DoubleChromosome> selectedPopulation = new ArrayList<DoubleChromosome>(this.population.size());
		double prob = 0;
		int positionSelected = 0;
		
		for (int i = 0; i < this.population.size(); i++) {
			prob = random.nextDouble();
			positionSelected = 0;
			
			while((positionSelected < this.population.size()) &&
					(prob > this.population.get(positionSelected).getAggregateScore()))
				positionSelected++;
			
			selectedPopulation.add(this.population.get(positionSelected).clone());
		}
		
		this.population = selectedPopulation;
	}

	@Override
	public void reproduction() {
		ArrayList<Integer> selectedCandidatesIdx = new ArrayList<Integer>(this.population.size());
		DoubleChromosome child1 = null, child2 = null;
		
		// select randomly all the candidates for the crossover
		for (int i = 0; i < this.population.size(); i++) {
			if(random.nextDouble() < this.crossProb)
				selectedCandidatesIdx.add(i);
		}
		
		// make size even
		if((selectedCandidatesIdx.size() % 2) == 1)
			selectedCandidatesIdx.remove(selectedCandidatesIdx.size() - 1);
		
		// iterate over pairs
		for (int i = 0; i < selectedCandidatesIdx.size(); i+=2) {
			DoubleChromosome parent1 = this.population.get(selectedCandidatesIdx.get(i));
			DoubleChromosome parent2 = this.population.get(selectedCandidatesIdx.get(i + 1));
			child1 = parent1.clone(); child1.setGenotype(new ArrayList<DoubleGene>());
			child2 = parent1.clone(); child2.setGenotype(new ArrayList<DoubleGene>());
			crossover(parent1, parent2, child1, child2);
			
			this.population.set(selectedCandidatesIdx.get(i), child1);
			this.population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}

	private void crossover(DoubleChromosome parent1, DoubleChromosome parent2,
			DoubleChromosome child1, DoubleChromosome child2) {
		
		int currentGeneLength = this.population.get(0).getLength();
			
		// select point over 0 and the current gene length - 1
		int crossoverPoint = random.nextInt(currentGeneLength);
		
		ArrayList<DoubleGene> parent1Gene = parent1.getGenotype();
		ArrayList<DoubleGene> parent2Gene = parent2.getGenotype();
		ArrayList<DoubleGene> child1Gene = new ArrayList<DoubleGene>(currentGeneLength);
		ArrayList<DoubleGene> child2Gene = new ArrayList<DoubleGene>(currentGeneLength);
		
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
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public void mutation() {
		ArrayList<DoubleGene> genes;
		boolean mutated = false;
		
		for (DoubleChromosome chrom : this.population) {
			mutated = false;
			genes = chrom.getGenotype();
			
			for (DoubleGene gene : genes) {
				mutated = gene.mutate(this.mutationProb, random);
			}
			
			if(mutated)
				chrom.setAptitude(chrom.evaluate());
		}
	}
	
	public String toString() {
		if(this.population == null || this.population.size() == 0) return "";
		String s = new String();
		
		s += "pos     genotype     aptitude phenotype" + System.lineSeparator();
		for (int i = 0; i < this.population.size(); i++) {
			DoubleChromosome elem = this.population.get(i);
			s += i + " " + elem + " " + String.format("%5f", elem.getAptitude()) + " ";
			
			for (Double el : elem.getPhenotype()) {
				s += String.format("%.5f", el) + " ";
			}
			s = s.substring(0, s.length() - 1);
			
			s += System.lineSeparator();
		}
		
		return s;
	}

}
