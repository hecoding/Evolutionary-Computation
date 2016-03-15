package model.chromosome;

import java.util.ArrayList;

import model.function.Function;
import model.gene.AbstractGene;

public abstract class AbstractChromosome<T extends AbstractGene<?>> {
	protected Function function;
	protected ArrayList<T> genes;
	protected ArrayList<Double> phenotype;
	protected double aptitude;
	protected double score;
	protected double aggregateScore;
	
	public AbstractChromosome() {
		this.aptitude = 0;
		this.score = 0;
		this.aggregateScore = 0;
	}
	
	public abstract void initialize();
	public abstract double evaluate();
	public abstract void refreshPhenotype();
	
	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public ArrayList<T> getGenotype() {
		return this.genes;
	}
	
	public void setGenotype(ArrayList<T> genes) {
		this.genes = genes;
		this.refreshPhenotype();
	}

	public ArrayList<Double> getPhenotype() {
		this.refreshPhenotype();
		
		return this.phenotype;
	}

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
}
