package model.Chromosome;

import java.util.ArrayList;
import java.util.Random;

public class BooleanChromosome extends AbstractChromosome<BooleanGene> {
	private int minx;
	private int maxx;
	private int length;
	private double tolerance;
	private Random random;
	
	public BooleanChromosome(int minx, int maxx, double tolerance, Random randomGenerator) {
		this.minx = minx;
		this.maxx = maxx;
		this.tolerance = tolerance;
		this.length = this.computeLength();
		this.genes = new ArrayList<BooleanGene>(this.length);
		this.random = randomGenerator;
	}

	@Override
	public void initialize() {
		if (!this.genes.isEmpty())
			this.genes.clear();
		
		for (int i = 0; i < this.length; i++) {
			this.genes.add( new BooleanGene(random.nextBoolean()) );
		}
	}

	@Override
	public double evaluate() {
		return this.f((double) this.getPhenotype());
	}
	
	@Override
	public void refreshPhenotype() {
		long chromosomeDecimal = chromo2dec(this.getListGenotype());
		
		this.phenotype = this.minx + (this.maxx - this.minx) * chromosomeDecimal / (Math.pow(2, this.length) - 1);
	}
	
	double f(double x) {
		// primera función
		//return -Math.abs(x * Math.sin( Math.sqrt(Math.abs(x)) ));
		return x / (1 + (x * x));
	}
	
	private final int computeLength() {
		return (int) Math.ceil( log2( 1 + (this.maxx - this.minx) / this.tolerance ) );
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
	
	private static final double log2(double n) {
		return Math.log(n)/Math.log(2.0);
	}
	
	public int getLength() {
		return this.length;
	}
	
	public BooleanChromosome clone() {
		return new BooleanChromosome(this.minx, this.maxx, this.tolerance, random);
	}
	
	public String toString() {
		String s = new String();
		boolean unitaryGenes = this.genes.get(0).information.size() == 1;
		
		for (BooleanGene gen : this.genes) {
			s = s + gen;
			if (!unitaryGenes) s += "|";
		}
		if (!unitaryGenes) s = s.substring(0, s.length() - 1);
		
		return s;
	}

}
