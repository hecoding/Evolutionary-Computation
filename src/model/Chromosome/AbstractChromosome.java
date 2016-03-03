package model.Chromosome;

import java.util.ArrayList;

public abstract class AbstractChromosome<T extends AbstractGene<?>> {
	private ArrayList<T> genes;
	private double phenotype;
	private double aptitude;
	private double score;
	private double aggregateScore;
	// getPhenotype using the double?
	// a method named refreshPhenotype?
	// setGenotype (also calling refreshPhenotype)?
	
	public abstract double evaluate();
	
	public ArrayList<T> getGenotype() {
		return this.genes;
	}
	
	public abstract Object getPhenotype();

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
