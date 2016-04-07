package model.geneticAlgorithm;

import java.util.ArrayList;
import model.chromosome.BooleanChromosome;
import model.function.Function;
import model.gene.BooleanGene;
import model.geneticAlgorithm.crossover.CrossoverInterface;
import model.geneticAlgorithm.selection.SelectionInterface;

public class BooleanGeneticAlgorithm extends AbstractGeneticAlgorithm<BooleanChromosome> {

	public BooleanGeneticAlgorithm() {
		
	}
	
	public BooleanGeneticAlgorithm(Function func, SelectionInterface selectionStrategy, CrossoverInterface crossoverStrategy,
			int populationNum, boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		super(func, selectionStrategy, crossoverStrategy, populationNum, useElitism, elitePercentage,
				maxGenerationNum, crossProb, mutationProb, tolerance, customSeed, seed);
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
	
	// selection
	
	// reproduction
	
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
