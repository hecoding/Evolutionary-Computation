package model.chromosome;

import java.util.ArrayList;
import java.util.Random;

import model.function.Function;
import model.gene.BooleanGene;

public class BooleanChromosome extends AbstractChromosome<BooleanGene> {
	private ArrayList<Integer> geneLengths;
	private double tolerance;
	private Random random;
	ArrayList<Double> params;
	
	public BooleanChromosome(Function function, double tolerance, Random randomGenerator) {
		this.function = function;
		this.tolerance = tolerance;
		this.geneLengths = this.computeGeneLengths();
		this.genes = new ArrayList<BooleanGene>(this.function.paramNum());
		this.phenotype = new ArrayList<Double>(this.function.paramNum());
		this.random = randomGenerator;
		this.params = new ArrayList<Double>(this.function.paramNum());
	}

	@Override
	public void initialize() {
		if (!this.genes.isEmpty())
			this.genes.clear();
		
		for (int i = 0; i < this.function.paramNum(); i++) {
			this.genes.add( new BooleanGene(this.geneLengths.get(i), this.random) );
		}
	}

	@Override
	public double evaluate() {
		params.clear();
		params.addAll(this.getPhenotype());
		return this.function.f(params);
	}
	
	@Override
	public void refreshPhenotype() {
		ArrayList<Long> geneDecimal = new ArrayList<Long>(this.genes.size());
		for (BooleanGene gene : this.genes) {
			geneDecimal.add(gene2dec(gene));
		}
		
		ArrayList<Double> phenotype = new ArrayList<Double>();
		for (int i = 0; i < this.genes.size(); i++) {
			double minx = this.function.getLimits().get(i).minx;
			double maxx = this.function.getLimits().get(i).maxx;
			
			phenotype.add(minx + (maxx - minx) * geneDecimal.get(i) / (Math.pow(2, this.geneLengths.get(i)) - 1));
		}
		
		this.phenotype = phenotype;
	}
	
	private final ArrayList<Integer> computeGeneLengths() {
		ArrayList<Integer> lengths = new ArrayList<Integer>(this.function.paramNum());
		
		for (int i = 0; i < this.function.paramNum(); i++) {
			lengths.add(computeLength(this.function.getLimits().get(i).minx, this.function.getLimits().get(i).maxx));
		}
		
		return lengths;
	}
	
	private final int computeLength(double minx, double maxx) {
		return (int) Math.ceil( log2( 1 + (maxx - minx) / this.tolerance ) );
	}
	
	private final long gene2dec(BooleanGene gene) {
		long geneDecimal = 0;
		
		for (Boolean bit : gene.getInformation()) {
			geneDecimal = geneDecimal * 2 + (bit ? 1 : 0);
		}
		
		return geneDecimal;
	}
	
	private static final double log2(double n) {
		return Math.log(n)/Math.log(2.0);
	}
	
	/* Retrieve the count of the bits of all the genes */
	public int getTotalLength() {
		int length = 0;
		for (Integer l : this.geneLengths) { // HACE FALTA ESTE ARRAY? GENE[i].SIZE()?
			length += l;
		}
		return length;
	}
	
	public int getGeneNum() {
		return this.genes.size();
	}
	
	public BooleanChromosome clone() {
		return new BooleanChromosome(this.function, this.tolerance, random);
	}
	
	public String toString() {
		String s = new String();
		
		for (BooleanGene gen : this.genes) {
			s = s + gen;
			s += "|";
		}
		s = s.substring(0, s.length() - 1);
		
		return s;
	}

}
