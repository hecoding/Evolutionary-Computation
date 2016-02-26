package model.Chromosome;

import java.util.ArrayList;

public class Gene extends AbstractGene<ArrayList<Boolean>> {
	
	@Override
	public Object getPhenotype() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < this.genotype.size(); i++) {
		    if (this.genotype.get(i)) builder.append("1"); else builder.append("0");
		}
		
		return builder.toString();
	}
	
}
