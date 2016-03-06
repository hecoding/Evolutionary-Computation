package model;

import java.util.ArrayList;
import java.util.Random;

import model.Chromosome.BooleanChromosome;
import model.Chromosome.BooleanGene;

public class GeneticAlgorithm { // TODO make it generic
	private ArrayList<BooleanChromosome> population;
	private int generationNum;
	private int maxGenerationNum;
	private BooleanChromosome bestChromosome;
	private double crossProb;
	private double mutationProb;
	private double tolerance;
	private long seed;
	private static Random randomGenerator;

	public GeneticAlgorithm(int populationNum, int maxGenerationNum, double crossProb, double mutationProb, double tolerance, long seed) {
		super();
		this.population = new ArrayList<BooleanChromosome>(populationNum);
		this.generationNum = 0;
		this.maxGenerationNum = maxGenerationNum;
		this.bestChromosome = null;
		this.crossProb = crossProb;
		this.mutationProb = mutationProb;
		this.tolerance = tolerance;
		this.seed = seed;
		randomGenerator = new Random(this.seed);
	}
	
	public void initialize() {
		for (int i = 0; i < this.population.size(); i++) {
			BooleanChromosome chr = new BooleanChromosome(-250, 250, this.tolerance, this.seed);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.set(i, chr);
		}
	}
	
	public void evaluatePopulation() {
		double currentAggregateScore = 0;
		double currentBestAptitude = 0;
		double currentAptitudeSum = 0;
		BooleanChromosome currentBest = null;
		
		for (BooleanChromosome chromosome : this.population) {
			double currentAptitude = chromosome.getAptitude();
			currentAptitudeSum += currentAptitude;
			
			if (currentAptitude > currentBestAptitude) {
				currentBest = chromosome;
				currentBestAptitude = currentAptitude;
			}
		}
		
		for (BooleanChromosome chromosome : this.population) {
			chromosome.setScore(chromosome.getAptitude() / currentAptitudeSum);
			chromosome.setAggregateScore(chromosome.getScore() + currentAggregateScore);
			currentAggregateScore += chromosome.getScore();
		}
		
		if (currentBestAptitude > this.bestChromosome.getAptitude()) {
			this.bestChromosome = currentBest;
		}
		// generalize for minimization handling also
	}
	
	public void increaseGeneration() {
		this.generationNum++;
	}
	
	public boolean finished() {
		return this.generationNum == this.maxGenerationNum;
	}
	
	/* GENETIC OPERATORS */
	
	public void selection() {
		// selección ruleta
		ArrayList<Integer> idxSelection = new ArrayList<Integer>(this.population.size());
		ArrayList<BooleanChromosome> selectedPopulation = new ArrayList<BooleanChromosome>(this.population.size());
		double prob;
		int positionSelected;
		
		for (int i = 0; i < this.population.size(); i++) {
			prob = randomGenerator.nextDouble();
			positionSelected = 0;
			
			while((prob > this.population.get(positionSelected).getAggregateScore())
					&& (positionSelected < this.population.size()))
				positionSelected++;
			
			idxSelection.set(i, positionSelected);
		}
		
		for (int i = 0; i < idxSelection.size(); i++) {
			int index = idxSelection.get(i);
			selectedPopulation.set(index, this.population.get(index));
		}
	}
	
	public void reproduction() {
		
	}
	
	public void mutation() {
		
	}

}
