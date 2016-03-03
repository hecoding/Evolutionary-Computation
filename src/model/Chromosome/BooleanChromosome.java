package model.Chromosome;

import java.util.ArrayList;

public class BooleanChromosome extends AbstractChromosome<BooleanGene> {
	private int minx;
	private int maxx;
	private int length;
	private double tolerance;
	
	public BooleanChromosome(int minx, int maxx, double tolerance) {
		this.minx = minx;
		this.maxx = maxx;
		this.tolerance = tolerance;
		this.length = this.computeLength();
	}

	@Override
	public double evaluate() {
		return this.f((double) this.getPhenotype());
	}

	@Override
	public Object getPhenotype() {
		long chromosomeDecimal = chromo2dec(this.getListGenotype());
		
		return this.minx + (this.maxx - this.minx) * chromosomeDecimal / (Math.pow(2, this.length) - 1);
	}
	
	double f(double x) {
		// primera función
		return -Math.abs(x * Math.sin( Math.sqrt(Math.abs(x)) ));
	}
	
	private final int computeLength() {
		return integralPart( log2( 1 + (this.maxx - this.minx) / this.tolerance ) );
	}
	
	private final long chromo2dec(ArrayList<Boolean> geneList) {
		long chromosomeDecimal = 0;
		
		for (Boolean bit : geneList) {
			chromosomeDecimal = chromosomeDecimal * 2 + (bit ? 1 : 0);
		}
		
		return chromosomeDecimal;
	}
	
	private ArrayList<Boolean> getListGenotype() {
		ArrayList<Boolean> geneList = new ArrayList<Boolean>();
		for (BooleanGene gen : this.getGenotype()) {
			geneList.addAll(gen.getInformation());
		}
		
		return geneList;
	}
	
	private static final int log2(double n) {
	    return (int)Math.floor(Math.log(n)/Math.log(2.0));
	}
	
	private static final int integralPart (double n) {
		return (int) (n - fractionalPart(n));
	}
	
	private static final double fractionalPart (double n) {
		return n % 1;
	}
	
	public String toString() {
		String s = new String();
		
		for (BooleanGene gen : this.getGenotype()) {
			s.concat(gen.toString());
		}
		
		return s;
	}

}
