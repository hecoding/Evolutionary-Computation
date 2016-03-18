package model.geneticAlgorithm;

import java.util.ArrayList;
import model.chromosome.BooleanChromosome;
import model.function.Function;
import model.gene.BooleanGene;

public class BooleanGeneticAlgorithm extends AbstractGeneticAlgorithm<BooleanChromosome> {

	public BooleanGeneticAlgorithm(Function func, int populationNum,
			boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		super(func, populationNum, useElitism, elitePercentage, maxGenerationNum,
				crossProb, mutationProb, tolerance, customSeed, seed);
	}
	
	public void initialize() {
		this.population.clear();
		
		for (int i = 0; i < this.populationNum; i++) {
			BooleanChromosome chr = new BooleanChromosome(function, this.tolerance, random);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.add(chr);
		}
	}
	
	/* GENETIC OPERATORS */
	
	public void selection() {
		// roulette selection
		ArrayList<BooleanChromosome> selectedPopulation = new ArrayList<BooleanChromosome>(this.population.size());
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
	
	public void reproduction() {
		ArrayList<Integer> selectedCandidatesIdx = new ArrayList<Integer>(this.population.size());
		BooleanChromosome child1 = null, child2 = null;
		
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
			BooleanChromosome parent1 = this.population.get(selectedCandidatesIdx.get(i));
			BooleanChromosome parent2 = this.population.get(selectedCandidatesIdx.get(i + 1));
			child1 = parent1.clone(); child1.setGenotype(new ArrayList<BooleanGene>());
			child2 = parent1.clone(); child2.setGenotype(new ArrayList<BooleanGene>());
			crossover(parent1, parent2, child1, child2);
			
			this.population.set(selectedCandidatesIdx.get(i), child1);
			this.population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}
	
	private void crossover(BooleanChromosome parent1, BooleanChromosome parent2, BooleanChromosome child1, BooleanChromosome child2) {
		int geneNum = this.population.get(0).getGeneNum();
		for (int i = 0; i < geneNum; i++) {
			int currentGeneLength = this.population.get(0).getGenotype().get(i).getLength();
			
			// select point over 0 and the current gene length - 1
			int crossoverPoint = random.nextInt(currentGeneLength);
			
			BooleanGene parent1Gene = parent1.getGenotype().get(i);
			BooleanGene parent2Gene = parent2.getGenotype().get(i);
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
	
	public void mutation() {
		ArrayList<BooleanGene> genes;
		boolean mutated = false;
		
		for (BooleanChromosome chrom : this.population) {
			mutated = false;
			genes = chrom.getGenotype();
			
			for (BooleanGene gene : genes) { // each gene can be coded on several bits
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
			BooleanChromosome elem = this.population.get(i);
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
