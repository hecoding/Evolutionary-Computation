package model.Chromosome;

public abstract class AbstractGene<T> {
	T genotype;
	
	public void setGenotype(T genotype) {
		this.genotype = genotype;
	}
	public T getGenotype() {
		return this.genotype;
	}
}
