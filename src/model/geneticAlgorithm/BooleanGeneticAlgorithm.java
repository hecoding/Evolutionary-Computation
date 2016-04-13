package model.geneticAlgorithm;

import model.chromosome.BooleanChromosome;
import model.function.Function;
import model.geneticAlgorithm.crossover.CrossoverInterface;
import model.geneticAlgorithm.mutation.MutationInterface;
import model.geneticAlgorithm.selection.SelectionInterface;

public class BooleanGeneticAlgorithm extends AbstractGeneticAlgorithm<BooleanChromosome> {

	public BooleanGeneticAlgorithm() {
		
	}
	
	public BooleanGeneticAlgorithm(Function func, SelectionInterface selectionStrategy, CrossoverInterface crossoverStrategy,
			MutationInterface mutationStrategy, int populationNum, boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		super(func, selectionStrategy, crossoverStrategy, mutationStrategy, populationNum, useElitism, elitePercentage,
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
	
	// mutation

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
