package model.Chromosome;

import java.util.ArrayList;

public class Gene extends AbstractGene<ArrayList<Boolean>> {
	
	@Override
	public Object getPhenotype() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		return this.genotype.toString();
	}
	
}
