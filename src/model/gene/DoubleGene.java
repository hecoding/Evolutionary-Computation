package model.gene;

import java.util.Random;

public class DoubleGene extends AbstractGene<Double> {
	public DoubleGene() {
	}
	
	/* Return empty gene with n allocated position */
	public DoubleGene(double n) {
		this.information = n;
	}
	
	public Object clone() {
		return this.information;
	}
	
	public String toString() {
		return this.information.toString();
	}

	public boolean mutate(double mutationProb, Random random, double range) {
		// uniform mutation ranging from 0 to 1
		boolean mutated = false;
		
		if(random.nextDouble() < mutationProb) {
			this.information = random.nextDouble() * range;
			mutated = true;
		}
		
		return mutated;
	}
}
