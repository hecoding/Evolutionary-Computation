package model.geneticAlgorithm;

import model.chromosome.DoubleChromosome;
import model.function.Function;
import model.geneticAlgorithm.crossover.CrossoverInterface;
import model.geneticAlgorithm.selection.SelectionInterface;

public class DoubleGeneticAlgorithm extends AbstractGeneticAlgorithm<DoubleChromosome> {

	public DoubleGeneticAlgorithm() {
	}
	
	public DoubleGeneticAlgorithm(Function func, SelectionInterface selectionStrategy, CrossoverInterface crossoverStrategy,
			int populationNum, boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		super(func, selectionStrategy, crossoverStrategy, populationNum, useElitism, elitePercentage,
				maxGenerationNum, crossProb, mutationProb, tolerance, customSeed, seed);
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
	
	/* GENETIC OPERATORS */
	
	// selection

	// reproduction

	@Override
	public void mutation() {
		/*ArrayList<DoubleGene> genes;
		boolean mutated = false;*/
		
		for (DoubleChromosome chrom : this.population) {
			/*mutated = false;
			genes = chrom.getGenotype();
			
			for (DoubleGene gene : genes) {
				mutated = gene.mutate(this.mutationProb, random);
			}
			
			if(mutated)
				chrom.setAptitude(chrom.evaluate());*/
			chrom.mutate();
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
