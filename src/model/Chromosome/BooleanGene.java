package model.Chromosome;

import java.util.ArrayList;

public class BooleanGene extends AbstractGene<ArrayList<Boolean>> {
	
	public BooleanGene() {
		this.information = new ArrayList<Boolean>();
	}
	
	public BooleanGene(Boolean b) {
		ArrayList<Boolean> info = new ArrayList<Boolean>();
		info.add(b);
		
		this.information = info;
	}
	
	public BooleanGene(ArrayList<Boolean> g) {
		this.information = g;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < this.information.size(); i++) {
		    if (this.information.get(i)) builder.append("1"); else builder.append("0");
		}
		
		return builder.toString();
	}
	
}
