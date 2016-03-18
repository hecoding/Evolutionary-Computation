package model.chromosome;

import java.util.ArrayList;
import java.util.Random;

import model.function.Function;
import model.gene.DoubleGene;

public class DoubleChromosome extends AbstractChromosome<DoubleGene> {
	
	private double tolerance;
	private ArrayList<Double> goodGenes;
	private Random random;
	private ArrayList<Double> params;
	private double maxRange;
	
	public DoubleChromosome(Function func, double tolerance, Random randomGenerator) {
		function = func;
		this.tolerance = tolerance;
		this.goodGenes = new ArrayList<Double>();
		this.random = randomGenerator;
		this.params = new ArrayList<Double>(function.paramNum());
		this.setMaxRange(function.paramNum() * 2); // function 4 specific
	}

	public DoubleChromosome(Function func, ArrayList<Double> genes, double tolerance, Random randomGenerator) {
		function = func;
		this.tolerance = tolerance;
		ArrayList<Double> clonedList = new ArrayList<Double>(genes.size());
		for (Double gene : genes) {
			clonedList.add(gene);
		}
		this.goodGenes = clonedList;
		this.random = randomGenerator;
		this.params = new ArrayList<Double>(function.paramNum());
		this.setMaxRange(function.paramNum() * 2);
	}

	@Override
	public void initialize() {
		if (!this.goodGenes.isEmpty())
			this.goodGenes.clear();
		
		for (int i = 0; i < function.paramNum(); i++) {
			this.goodGenes.add(random.nextDouble() * this.getMaxRange());
		}
	}

	@Override
	public double evaluate() {
		params.clear();
		params.addAll(this.getPhenotype());
		return function.f(params);
	}
	
	public int getLength() {
		return this.goodGenes.size();
	}
	
	public ArrayList<Double> getPhenotype() {		
		return this.goodGenes;
	}
	
	public ArrayList<DoubleGene> getGenotype() {
		ArrayList<DoubleGene> a = new ArrayList<DoubleGene>();
		for (Double gene : this.goodGenes) {
			a.add(new DoubleGene(gene));
		}
		return a;
	}
	
	public void setGenotype(ArrayList<DoubleGene> genes) {
		ArrayList<Double> a = new ArrayList<Double>();
		for (DoubleGene doubleGene : genes) {
			a.add(doubleGene.getInformation());
		}
		this.goodGenes = a;
		this.refreshPhenotype();
	}
	
	public DoubleChromosome clone() {
		DoubleChromosome chr = new DoubleChromosome(function, this.goodGenes, this.tolerance, random);
		chr.setAptitude(this.getAptitude());
		
		return chr;
	}
	
	public double getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(double maxRange) {
		this.maxRange = maxRange;
	}

	public String toString() {
		String s = new String("");
		
		for (Double gen : this.goodGenes) {
			s = s + gen;
			s += "|";
		}
		if(!s.isEmpty())
			s = s.substring(0, s.length() - 1);
		
		return s;
	}

	@Override
	public void refreshPhenotype() {
		// TODO Auto-generated method stub
		
	}

}
