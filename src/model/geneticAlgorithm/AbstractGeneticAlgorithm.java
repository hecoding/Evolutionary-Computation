package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import model.chromosome.AbstractChromosome;
import model.chromosome.comparator.AptitudeComparator;
import model.function.Function;
import model.geneticAlgorithm.selection.SelectionInterface;
import model.observer.GeneticAlgorithmObserver;
import model.observer.Observable;

public abstract class AbstractGeneticAlgorithm<T extends AbstractChromosome<?>> implements Observable<GeneticAlgorithmObserver> {
	protected ArrayList<T> population;
	protected static Function function;
	protected int populationNum;
	protected double tolerance;
	protected int currentGeneration;
	protected int maxGenerationNum;
	protected double crossProb;
	protected double mutationProb;
	protected boolean customSeed;
	protected long seed;
	protected boolean useElitism;
	protected double elitePercentage;
	protected ArrayList<Double> inspectedAptitude;
	protected static Random random;
	protected ArrayList<GeneticAlgorithmObserver> observers;
	
	protected ArrayList<T> elite;
	protected T bestChromosome;
	protected double bestAptitude;
	protected double averageAptitude;
	protected double averageScore;
	protected static Comparator<AbstractChromosome<?>> aptitudeComparator;
	protected ArrayList<Double> bestChromosomeList;
	protected ArrayList<Double> averageAptitudeList;
	protected ArrayList<Double> bestAptitudeList;
	
	protected SelectionInterface selectionStrategy;
	
	public AbstractGeneticAlgorithm() {
		this.observers = new ArrayList<GeneticAlgorithmObserver>();
	}
	
	public AbstractGeneticAlgorithm(Function func, SelectionInterface selectionStrategy, int populationNum,
			boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		function = func;
		this.selectionStrategy = selectionStrategy;
		this.populationNum = populationNum;
		this.useElitism = useElitism;
		this.elitePercentage = elitePercentage;
		this.population = new ArrayList<T>(populationNum);
		this.elite = null;
		this.currentGeneration = 0;
		this.maxGenerationNum = maxGenerationNum;
		this.bestChromosome = null;
		this.inspectedAptitude = new ArrayList<Double>(populationNum);
		this.bestAptitude = 0;
		this.averageAptitude = 0;
		this.averageScore = 0;
		this.crossProb = crossProb;
		this.mutationProb = mutationProb;
		this.tolerance = tolerance;
		this.customSeed = customSeed;
		this.seed = seed;
		this.bestChromosomeList = new ArrayList<Double>(this.maxGenerationNum);
		this.averageAptitudeList = new ArrayList<Double>(this.maxGenerationNum);
		this.bestAptitudeList = new ArrayList<Double>(this.maxGenerationNum);
		this.observers = new ArrayList<GeneticAlgorithmObserver>();
		
		if(random == null) {
			if(customSeed)
				random = new Random(this.seed);
			else
				random = new Random();
		}
		if(aptitudeComparator == null)
			aptitudeComparator = new AptitudeComparator();
	}
	
	public void selection() {
		this.selectionStrategy.select(this.population, random);
	}
	public abstract void reproduction();
	public abstract void mutation();
	
	public void restart(Function func, SelectionInterface selectionStrategy, int populationNum, boolean useElitism,
			double elitePercentage, int maxGenerationNum, double crossProb,
			double mutationProb, double tolerance, boolean customSeed, long seed) {
		function = func;
		this.selectionStrategy = selectionStrategy;
		this.populationNum = populationNum;
		this.useElitism = useElitism;
		this.elitePercentage = elitePercentage;
		this.population = new ArrayList<T>(populationNum);
		this.elite = null;
		this.currentGeneration = 0;
		this.maxGenerationNum = maxGenerationNum;
		this.bestChromosome = null;
		this.bestAptitude = 0;
		this.averageAptitude = 0;
		this.averageScore = 0;
		this.crossProb = crossProb;
		this.mutationProb = mutationProb;
		this.tolerance = tolerance;
		this.customSeed = customSeed;
		this.seed = seed;
		this.bestChromosomeList = new ArrayList<Double>(this.maxGenerationNum);
		this.averageAptitudeList = new ArrayList<Double>(this.maxGenerationNum);
		this.bestAptitudeList = new ArrayList<Double>(this.maxGenerationNum);
		this.inspectedAptitude = new ArrayList<Double>(populationNum);
		
		this.initialize();
	}
	
	public void increaseGeneration() {
		this.currentGeneration++;
	}
	
	public boolean finished() {
		return this.currentGeneration == this.maxGenerationNum;
	}

	public Function getFunction() {
		return function;
	}
	
	public SelectionInterface getSelectionStrategy() {
		return this.selectionStrategy;
	}

	public int getPopulationNum() {
		return populationNum;
	}

	public double getTolerance() {
		return tolerance;
	}

	public int getMaxGenerationNum() {
		return maxGenerationNum;
	}

	public double getCrossProb() {
		return crossProb;
	}

	public double getMutationProb() {
		return mutationProb;
	}

	public boolean isCustomSeed() {
		return customSeed;
	}

	public long getSeed() {
		return seed;
	}

	public boolean isUseElitism() {
		return useElitism;
	}

	public double getElitePercentage() {
		return elitePercentage;
	}
	
	public void addObserver(GeneticAlgorithmObserver o) {
		this.observers.add(o);
	}
	
	public void removeObserver(GeneticAlgorithmObserver o) {
		this.observers.remove(o);
	}
	
	protected void notifyStartRun() {
		for (GeneticAlgorithmObserver obs : this.observers) {
			obs.onStartRun();
		}
	}
	
	protected void notifyEndRun() {
		for (GeneticAlgorithmObserver obs : this.observers) {
			obs.onEndRun();
		}
	}

	public abstract void initialize();

	public void run() {
		this.notifyStartRun();
		
		this.initialize();
		if(function.isMinimization())
			this.aptitudeShifting();
		this.evaluatePopulation();
		
		while(!this.finished()) {
			if (this.useElitism)
				this.elite = this.selectElite();
			this.increaseGeneration();
			this.selection();
			this.reproduction();
			this.mutation();
			if (this.useElitism)
				this.includeElite(this.elite);
			if(function.isMinimization())
				this.aptitudeShifting();
			this.evaluatePopulation();
			
			this.gatherStatistics();
		}
		
		this.notifyEndRun();
	}

	protected void gatherStatistics() {
		this.bestChromosomeList.add(this.getBestChromosome().getAptitude());
		this.bestAptitudeList.add(this.getBestAptitude());
		this.averageAptitudeList.add(this.getAverageAptitude());
	}
	
	@SuppressWarnings("unchecked")
	public void evaluatePopulation() { // VERY IMPORTANT TO CLEAR inspectedAptitude;
		double aggregateScore = 0;
		double bestAptitude = Double.NEGATIVE_INFINITY;
		if(function.isMinimization())
			bestAptitude = Double.POSITIVE_INFINITY;
		double aggregateAptitude = 0;
		double aggregateInspectedAptitude = 0;
		T currentBest = null;
		
		// compute best and aggregate aptitude
		int i = 0;
		for (T chromosome : this.population) {
			double chromAptitude = chromosome.getAptitude();
			aggregateAptitude += chromAptitude;
			if(function.isMinimization())
				aggregateInspectedAptitude += this.inspectedAptitude.get(i);
			
			if(function.isMinimization()) {
				if (chromAptitude < bestAptitude) {
					currentBest = chromosome;
					bestAptitude = chromAptitude;
				}
			}
			else {
				if (chromAptitude > bestAptitude) {
					currentBest = chromosome;
					bestAptitude = chromAptitude;
				}
			}
			i++;
		}
		
		// compute and set score of population individuals
		i = 0;
		for (T chromosome : this.population) {
			if(function.isMinimization())
				chromosome.setScore(this.inspectedAptitude.get(i) / aggregateInspectedAptitude);
			else
				chromosome.setScore(chromosome.getAptitude() / aggregateAptitude);
			chromosome.setAggregateScore(chromosome.getScore() + aggregateScore);
			aggregateScore += chromosome.getScore();
			i++;
		}
		
		// refresh best individual and aptitude statistics
		if(function.isMinimization()) {
			if (this.bestChromosome == null || bestAptitude < this.bestChromosome.getAptitude()) {
				this.bestChromosome = (T) currentBest.clone();
			}
		}
		else {			
			if (this.bestChromosome == null || bestAptitude > this.bestChromosome.getAptitude()) {
				this.bestChromosome = (T) currentBest.clone();
			}
		}
		
		this.bestAptitude = bestAptitude;
		this.averageAptitude = aggregateAptitude / this.population.size();
		this.averageScore = aggregateScore / this.population.size();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<T> selectElite() {
		int eliteNum = (int) Math.ceil(this.populationNum * this.elitePercentage);
		ArrayList<T> elite = new ArrayList<T>(eliteNum);
		
		this.population.sort(aptitudeComparator);
		if(function.isMinimization()) {
			for (int i = 0; i < eliteNum; i++) {
				elite.add((T) this.population.get(i).clone());
			}
		}
		else {			
			for (int i = this.populationNum - eliteNum; i < this.populationNum; i++) {
				elite.add((T) this.population.get(i).clone());
			}
		}
		
		return elite;
	}
	
	public void includeElite(ArrayList<T> elite) {
		// substitution of the worst adapted individuals
		this.population.sort(aptitudeComparator);
		
		if(function.isMinimization()) {
			int j = 0;
			for (int i = this.populationNum - elite.size(); i < this.populationNum; i++) {
				this.population.set(i, elite.get(j));
				j++;
			}
		}
		else {
			for (int i = 0; i < elite.size(); i++) {
				this.population.set(i, elite.get(i));
			}			
		}
	}
	
	/* transform minimization problem into maximization */
	private void aptitudeShifting() {
		this.inspectedAptitude.clear();
		Double cmax = Double.NEGATIVE_INFINITY;
		
		for (T chrom : this.population) {
			if(chrom.getAptitude() > cmax)
				cmax = chrom.getAptitude(); // get worst aptitude
		}
		cmax = cmax * 1.05; // avoid aggregateAptitude = 0 when population converges
		
		for (T chrom : this.population) {
			this.inspectedAptitude.add(cmax - chrom.getAptitude());
		}
	}

	public T getBestChromosome() {
		return bestChromosome;
	}
	
	public double getBestAptitude() {
		return this.bestAptitude;
	}
	
	public double getAverageAptitude() {
		return this.averageAptitude;
	}
	
	public double getAverageScore() {
		return this.averageScore;
	}

	public ArrayList<Double> getBestChromosomeList() {
		return bestChromosomeList;
	}

	public ArrayList<Double> getAverageAptitudeList() {
		return averageAptitudeList;
	}

	public ArrayList<Double> getBestAptitudeList() {
		return bestAptitudeList;
	}
}
