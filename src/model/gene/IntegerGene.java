package model.gene;

public class IntegerGene extends AbstractGene<Integer> {

	public IntegerGene(int n) {
		this.information = new Integer(n);
	}
	
	@Override
	public boolean equals(Object o) {
		// using in contains() for chromosome
		return this.information.intValue() == ((IntegerGene) o).information.intValue();
	}

	@Override
	public IntegerGene clone() {
		return new IntegerGene(this.information.intValue());
	}
	
	public String toString() {
		return this.information.toString();
	}
}
