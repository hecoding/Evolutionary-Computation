package model.Chromosome;

import java.util.ArrayList;

public abstract class AbstractChromosome<T extends AbstractGene<?>> {
	protected ArrayList<T> genes;
	protected double phenotype;
	protected double aptitude;
	protected double score;
	protected double aggregateScore;
	
	public AbstractChromosome() {
		this.phenotype = 0;
		this.aptitude = 0;
		this.score = 0;
		this.aggregateScore = 0;
	}
	
	public abstract void initialize();
	public abstract double evaluate();
	public abstract void refreshPhenotype();
	
	public ArrayList<T> getGenotype() {
		return this.genes;
	}
	
	public void setGenotype(ArrayList<T> genes) {
		this.genes = genes;
		this.refreshPhenotype();
	}

	public double getPhenotype() {
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
