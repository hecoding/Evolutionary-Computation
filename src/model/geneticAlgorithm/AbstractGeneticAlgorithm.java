package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Random;

import model.function.Function;
import model.observer.GeneticAlgorithmObserver;
import model.observer.Observable;

public abstract class AbstractGeneticAlgorithm implements Observable<GeneticAlgorithmObserver> {
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
	protected static Random random;
	protected ArrayList<GeneticAlgorithmObserver> observers;
	
	public AbstractGeneticAlgorithm() {
		this.observers = new ArrayList<GeneticAlgorithmObserver>();
	}
	
	public abstract void selection();
	public abstract void reproduction();
	public abstract void mutation();
	
	public abstract void restart(Function func, int populationNum, boolean useElitism,
			double elitePercentage, int maxGenerationNum, double crossProb, double mutationProb,
			double tolerance, boolean customSeed, long seed);
	
	public void increaseGeneration() {
		this.currentGeneration++;
	}
	
	public boolean finished() {
		return this.currentGeneration == this.maxGenerationNum;
	}

	public Function getFunction() {
		return function;
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

	public abstract void run();

	public abstract Object getBestChromosome();

	public abstract ArrayList<Double> getBestChromosomeList();

	public abstract ArrayList<Double> getBestAptitudeList();

	public abstract ArrayList<Double> getAverageAptitudeList();
}
