package model.genProgAlgorithm;

import model.Map;
import model.chromosome.AntTrailChromosome;
import model.genProgAlgorithm.crossover.CrossoverInterface;
import model.genProgAlgorithm.fitnessFunction.FitnessFunctionInterface;
import model.genProgAlgorithm.mutation.MutationInterface;
import model.genProgAlgorithm.selection.SelectionInterface;

public class AntTrailGeneticAlgorithm extends AbstractGeneticAlgorithm<AntTrailChromosome> {
	private static Map originalMap;

	public AntTrailGeneticAlgorithm(Map map, FitnessFunctionInterface func, SelectionInterface selectionStrategy, CrossoverInterface crossoverStrategy,
			MutationInterface mutationStrategy, int populationNum, boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb) {
		super(func, selectionStrategy, crossoverStrategy, mutationStrategy, populationNum, useElitism, elitePercentage,
				maxGenerationNum, crossProb, mutationProb);
		
		originalMap = map;
	}

	@Override
	public void initialize() {
		this.population.clear();
		
		for (int i = 0; i < this.populationNum; i++) {
			AntTrailChromosome chr = new AntTrailChromosome(this.fitnessFunc);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.add(chr);
		}
	}
	
	public String toString() {
		if(this.population == null || this.population.size() == 0) return "";
		String s = new String();
		
		s += "pos aptitude phenotype" + System.lineSeparator();
		for (int i = 0; i < this.population.size(); i++) {
			AntTrailChromosome elem = this.population.get(i);
			s += i + " " +
				 String.format("%5f", elem.getAptitude()) + " " +
				 elem.getPhenotype() + System.lineSeparator();
		}
		
		return s;
	}
	
	public static Map getMap() {
		return originalMap.clone();
	}

}
