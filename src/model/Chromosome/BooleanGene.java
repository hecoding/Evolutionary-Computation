package model.Chromosome;

import java.util.ArrayList;

public class BooleanGene extends AbstractGene<ArrayList<Boolean>> {
	
	public BooleanGene() {
		this.setGenotype(new ArrayList<Boolean>());
	}
	
	public BooleanGene(ArrayList<Boolean> g) {
		this.setGenotype(g);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < this.genotype.size(); i++) {
		    if (this.genotype.get(i)) builder.append("1"); else builder.append("0");
		}
		
		return builder.toString();
	}
	
}
