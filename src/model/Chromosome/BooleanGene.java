package model.Chromosome;

import java.util.ArrayList;

public class BooleanGene extends AbstractGene<ArrayList<Boolean>> {
	
	public BooleanGene() {
		this.setInformation(new ArrayList<Boolean>());
	}
	
	public BooleanGene(ArrayList<Boolean> g) {
		this.setInformation(g);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		ArrayList<Boolean> info = this.getInformation();
		
		for (int i = 0; i < info.size(); i++) {
		    if (info.get(i)) builder.append("1"); else builder.append("0");
		}
		
		return builder.toString();
	}
	
}
