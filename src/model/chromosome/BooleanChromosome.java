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
		params.add((double) this.getPhenotype());
		return this.function.f(params);
	}
	
	@Override
	public void refreshPhenotype() {
		long chromosomeDecimal = chromo2dec(this.getListGenotype());
		double minx = this.function.getLimits().get(0).minx;
		double maxx = this.function.getLimits().get(0).maxx;
		
		this.phenotype = minx + (maxx - minx) * chromosomeDecimal / (Math.pow(2, this.geneLengths) - 1);
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
		boolean unitaryGenes = this.genes.get(0).getInformation().size() == 1;
		
		for (BooleanGene gen : this.genes) {
			s = s + gen;
			if (!unitaryGenes) s += "|";
		}
		if (!unitaryGenes) s = s.substring(0, s.length() - 1);
		
		return s;
	}

}
