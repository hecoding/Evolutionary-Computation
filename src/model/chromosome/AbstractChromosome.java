package model.chromosome;

import java.util.ArrayList;

import model.gene.AbstractGene;
import model.genProgAlgorithm.fitnessFunction.FitnessFunctionInterface;

public abstract class AbstractChromosome<T extends AbstractGene<?>> implements Cloneable {
	protected static FitnessFunctionInterface fitnessFunc;
	protected ArrayList<T> genes;
	protected double aptitude;
	protected double score;
	protected double aggregateScore;
	
	public AbstractChromosome() {
		this.aptitude = 0;
		this.score = 0;
		this.aggregateScore = 0;
	}
	
	public abstract void initialize();
	
	public double evaluate() {
		return fitnessFunc.f(this.getPhenotype());
	}
	
	public FitnessFunctionInterface getFunction() {
		return fitnessFunc;
	}

	public void setFunction(FitnessFunctionInterface func) {
		fitnessFunc = func;
	}
	
	public void add(T gene) {
		this.genes.add(gene);
	}

	public ArrayList<T> getGenotype() {
		return this.genes;
	}
	
	public void setGenotype(ArrayList<T> genes) {
		this.genes = genes;
	}

	public abstract ArrayList<Double> getPhenotype();

	public double getAptitude() {
		return aptitude;
	}

	public void setAptitude(double aptitude) {
		this.aptitude = aptitude;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getAggregateScore() {
		return aggregateScore;
	}

	public void setAggregateScore(double aggregateScore) {
		this.aggregateScore = aggregateScore;
	}

	public int getLength() {
		return this.genes.size();
	}
	
	public abstract AbstractChromosome<T> clone();
	
	public abstract String toString();
}
