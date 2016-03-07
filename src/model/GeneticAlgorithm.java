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
	private static Random random;

	public GeneticAlgorithm(int populationNum, int maxGenerationNum, double crossProb, double mutationProb, double tolerance, long seed) {
		this.population = new ArrayList<BooleanChromosome>(populationNum);
		this.generationNum = 0;
		this.maxGenerationNum = maxGenerationNum;
		this.bestChromosome = null;
		this.crossProb = crossProb;
		this.mutationProb = mutationProb;
		this.tolerance = tolerance;
		this.seed = seed;
		
		if(random == null)
			random = new Random(this.seed);
	}
	
	public void initialize() {
		for (int i = 0; i < this.population.size(); i++) {
			BooleanChromosome chr = new BooleanChromosome(-250, 250, this.tolerance, random);
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
		ArrayList<BooleanChromosome> selectedPopulation = new ArrayList<BooleanChromosome>(this.population.size());
		double prob = 0;
		int positionSelected = 0;
		
		for (int i = 0; i < this.population.size(); i++) {
			prob = random.nextDouble();
			positionSelected = 0;
			
			while((prob > this.population.get(positionSelected).getAggregateScore())
					&& (positionSelected < this.population.size()))
				positionSelected++;
			
			selectedPopulation.set(i, this.population.get(positionSelected));
		}
		
		this.population = selectedPopulation;
	}
	
	public void reproduction() {
		ArrayList<Integer> selectedPopulation = new ArrayList<Integer>(this.population.size());
		int num_sele_cruce = 0;
		double prob;
		BooleanChromosome hijo1 = null, hijo2 = null;
		
		for (int i = 0; i < this.population.size(); i++) {
			prob = this.random.nextDouble();
			
			if(prob < this.crossProb) {
				selectedPopulation.set(num_sele_cruce, i);
				num_sele_cruce++;
			}
		}
		
		// hacer par
		if((num_sele_cruce % 2) == 1)
			num_sele_cruce--;
		
		for (int i = 0; i < num_sele_cruce; i+=2) {
			cruce();
			
			this.population.set(selectedPopulation.get(i), hijo1);
			this.population.set(selectedPopulation.get(i + 1), hijo2);
		}
	}
	
	private void cruce() {
		int punto_cruce = this.random.nextInt(this.population.get(0).getLength());
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

}
