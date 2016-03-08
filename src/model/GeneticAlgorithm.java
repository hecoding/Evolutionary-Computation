package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import model.Chromosome.AptitudeComparator;
import model.Chromosome.BooleanChromosome;
import model.Chromosome.BooleanGene;

public class GeneticAlgorithm {
	private int populationNum;
	private double eliteSize;
	private ArrayList<BooleanChromosome> population;
	private int generationNum;
	private int maxGenerationNum;
	private BooleanChromosome bestChromosome;
	private double crossProb;
	private double mutationProb;
	private double tolerance;
	private long seed;
	private static Random random;
	private static Comparator<BooleanChromosome> aptitudeComparator;

	public GeneticAlgorithm(int populationNum, double eliteSize, int maxGenerationNum, double crossProb, double mutationProb, double tolerance, long seed) {
		this.populationNum = populationNum;
		this.eliteSize = eliteSize;
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
		if(aptitudeComparator == null)
			aptitudeComparator = new AptitudeComparator();
	}
	
	public void initialize() {
		this.population.clear();
		
		for (int i = 0; i < this.populationNum; i++) {
			// BooleanChromosome chr = new BooleanChromosome(-250, 250, this.tolerance, random);
			BooleanChromosome chr = new BooleanChromosome(0, 20, this.tolerance, random);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.add(chr);
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
		
		if (this.bestChromosome == null || currentBestAptitude > this.bestChromosome.getAptitude()) {
			this.bestChromosome = currentBest;
		}
	}
	
	public void increaseGeneration() {
		this.generationNum++;
	}
	
	public boolean finished() {
		return this.generationNum == this.maxGenerationNum;
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
			
			while((prob > this.population.get(positionSelected).getAggregateScore())
					&& (positionSelected < this.population.size()))
				positionSelected++;
			
			selectedPopulation.add(this.population.get(positionSelected));
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
			child1 = parent1.clone();
			child2 = parent1.clone();
			crossover(parent1, parent2, child1, child2);
			
			this.population.set(selectedCandidatesIdx.get(i), child1);
			this.population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}
	
	private void crossover(BooleanChromosome parent1, BooleanChromosome parent2, BooleanChromosome child1, BooleanChromosome child2) {
		int chromLength = this.population.get(0).getLength();
		// select point over 0 and chromosomeLength - 1
		int crossoverPoint = random.nextInt(chromLength);
		
		ArrayList<BooleanGene> parent1Genes = parent1.getGenotype();
		ArrayList<BooleanGene> parent2Genes = parent2.getGenotype();
		ArrayList<BooleanGene> child1Genes = new ArrayList<BooleanGene>(chromLength);
		ArrayList<BooleanGene> child2Genes = new ArrayList<BooleanGene>(chromLength);
		
		// before the crossover point
		for (int i = 0; i < crossoverPoint; i++) {
			child1Genes.add(parent1Genes.get(i));
			child2Genes.add(parent2Genes.get(i));
		}
		
		// after the crossover point
		for (int i = crossoverPoint; i < chromLength; i++) {
			child1Genes.add(parent2Genes.get(i));
			child2Genes.add(parent1Genes.get(i));
		}
		
		child1.setGenotype(child1Genes);
		child2.setGenotype(child2Genes);
		
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
	
	public ArrayList<BooleanChromosome> selectElite() {
		int eliteNum = (int) Math.ceil(this.populationNum * this.eliteSize);
		ArrayList<BooleanChromosome> elite = new ArrayList<BooleanChromosome>(eliteNum);
		
		this.population.sort(aptitudeComparator);
		for (int i = this.populationNum - eliteNum; i < this.populationNum; i++) {
			elite.add(this.population.get(i));
		}
		
		return elite;
	}
	
	public void includeElite(ArrayList<BooleanChromosome> elite) {
		// TODO Auto-generated method stub
		
	}
	
	public BooleanChromosome getBestChromosome() {
		return bestChromosome;
	}

	public String toString() {
		String s = new String();
		
		s += "pos     genotype     aptitude phenotype" + System.lineSeparator();
		for (int i = 0; i < this.population.size(); i++) {
			BooleanChromosome elem = this.population.get(i);
			s += i + " " + elem + " " + String.format("%5f", elem.getAptitude()) + " " + String.format("%.5f", elem.getPhenotype()) + System.lineSeparator();
		}
		
		return s;
	}
	
	// TODO list
	// make the algorithm generic
	// generalize evaluatePopulation() for minimization handling also

}
