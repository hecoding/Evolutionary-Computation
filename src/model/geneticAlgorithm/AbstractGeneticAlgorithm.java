package model.geneticAlgorithm;

import java.util.Random;

public abstract class AbstractGeneticAlgorithm {
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
	
	public abstract void selection();
	public abstract void reproduction();
	public abstract void mutation();
	
	public void increaseGeneration() {
		this.currentGeneration++;
	}
	
	public boolean finished() {
		return this.currentGeneration == this.maxGenerationNum;
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
}
