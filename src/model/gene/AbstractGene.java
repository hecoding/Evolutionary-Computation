package model.gene;

import java.util.Random;

public abstract class AbstractGene<T> {
	protected T information;
	
	public abstract boolean mutate(double mutationProb, Random random);

	public T getInformation() {
		return information;
	}

	public void setInformation(T information) {
		this.information = information;
	}
}
