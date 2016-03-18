package model.gene;

import java.util.Random;

public class DoubleGene extends AbstractGene<Double> {
	private static double range;
	
	public DoubleGene() {
	}
	
	/* Return empty gene with n allocated position */
	public DoubleGene(double n) {
		this.information = n;
	}
	
	public boolean mutate(double mutationProb, Random random) {
		boolean mutated = false;
		
		if(random.nextDouble() < mutationProb) {
			this.information = random.nextDouble() * range;
			mutated = true;
		}
		
		return mutated;
	}
	
	public DoubleGene clone() {
		DoubleGene newGene = new DoubleGene(this.information);
		
		return newGene;
	}
	
	public void setMaxRange(double ran) {
		range = ran;
	}
	
	public static double getMaxRange() {
		return range;
	}

	public static void setRange(double range) {
		DoubleGene.range = range;
	}

	public String toString() {
		return this.information.toString();
	}

}
