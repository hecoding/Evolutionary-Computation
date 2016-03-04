package model.Chromosome;

public abstract class AbstractGene<T> {
	protected T information;

	public T getInformation() {
		return information;
	}

	public void setInformation(T information) {
		this.information = information;
	}
}
