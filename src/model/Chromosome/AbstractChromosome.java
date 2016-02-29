package model.Chromosome;

import java.util.ArrayList;

public abstract class AbstractChromosome<T extends AbstractGene<?>> {
	public ArrayList<T> genes;
	double phenotype;
	double aptitude;
	double score;
	double aggregateScore;
	
	public abstract double evalua();
	public abstract Object phenotype();
}
