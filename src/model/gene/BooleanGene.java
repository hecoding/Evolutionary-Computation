package model.gene;

import java.util.ArrayList;
import java.util.Random;

public class BooleanGene extends AbstractGene<ArrayList<Boolean>> {
	
	/* Return empty gene */
	public BooleanGene() {
		this.information = new ArrayList<Boolean>();
	}
	
	/* Return empty gene with n allocated position */
	public BooleanGene(int n) {
		this.information = new ArrayList<Boolean>(n);
	}
	
	public BooleanGene(Boolean b) {
		ArrayList<Boolean> info = new ArrayList<Boolean>();
		info.add(b);
		
		this.information = info;
	}
	
	public BooleanGene(ArrayList<Boolean> g) {
		this.information = g;
	}
	
	public BooleanGene(Integer length, Random random) {
		this.information = new ArrayList<Boolean>(length);
		
		for (int i = 0; i < length; i++) {
			this.information.add(random.nextBoolean());
		}
	}
	
	public void add(Boolean b) {
		this.information.add(b);
	}

	public boolean mutate(double mutationProb, Random random) {
		boolean mutated = false;
		
		for (Boolean bit : this.information) {
			if(random.nextDouble() < mutationProb) {
				bit = !bit;
				mutated = true;
			}
		}
		
		return mutated;
	}
	
	public int getLength() {
		return this.information.size();
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < this.information.size(); i++) {
		    if (this.information.get(i)) builder.append("1"); else builder.append("0");
		}
		
		return builder.toString();
	}
	
}
