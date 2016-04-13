package model.gene;

public class DoubleGene extends AbstractGene<Double> {
	private double minLimit;
	private double maxLimit;
	
	public DoubleGene() {
	}
	
	/* Return empty gene with n allocated position */
	public DoubleGene(double n) {
		this.information = new Double(n);
	}
	
	public DoubleGene clone() {
		DoubleGene chr = new DoubleGene(this.information);
		chr.setMinLimit(minLimit);
		chr.setMaxLimit(maxLimit);
		
		return chr;
	}
	
	public void setMinLimit(double lim) {
		minLimit = lim;
	}
	
	public double getMinLimit() {
		return minLimit;
	}
	
	public void setMaxLimit(double lim) {
		maxLimit = lim;
	}
	
	public double getMaxLimit() {
		return maxLimit;
	}

	public String toString() {
		return this.information.toString();
	}

}
