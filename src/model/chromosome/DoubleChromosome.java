package model.chromosome;

import java.util.ArrayList;
import java.util.Random;

import model.function.Function;
import model.gene.DoubleGene;

public class DoubleChromosome extends AbstractChromosome<DoubleGene> {
	
	private double tolerance;
	private Random random;
	
	public DoubleChromosome(Function func, double tolerance, Random randomGenerator) {
		function = func;
		this.tolerance = tolerance;
		this.genes = new ArrayList<DoubleGene>();
		this.random = randomGenerator;
		this.params = new ArrayList<Double>(function.paramNum());
	}

	public DoubleChromosome(Function func, ArrayList<DoubleGene> genes, double tolerance, Random randomGenerator) {
		function = func;
		this.tolerance = tolerance;
		ArrayList<DoubleGene> clonedList = new ArrayList<DoubleGene>(genes.size());
		for (DoubleGene gene : genes) {
			clonedList.add(gene.clone());
		}
		this.genes = clonedList;
		this.random = randomGenerator;
		this.params = new ArrayList<Double>(function.paramNum());
	}

	@Override
	public void initialize() {
		if (!this.genes.isEmpty())
			this.genes.clear();
		
		for (int i = 0; i < function.paramNum(); i++) {
			this.genes.add(new DoubleGene( function.getLimits().get(i).minx +
					random.nextDouble() * (function.getLimits().get(i).maxx - function.getLimits().get(i).minx) ));
			
			this.genes.get(i).setMinLimit(function.getLimits().get(i).minx);
			this.genes.get(i).setMaxLimit(function.getLimits().get(i).maxx);
		}
	}
	
	public ArrayList<Double> getPhenotype() {
		ArrayList<Double> a = new ArrayList<Double>();
		
		for (DoubleGene gene : this.genes) {
			a.add(gene.getInformation());
		}
		
		return a;
	}
	
	public DoubleChromosome clone() {
		// deep copy indeed
		DoubleChromosome chr = new DoubleChromosome(function, this.genes, this.tolerance, random);
		
		chr.aggregateScore = this.aggregateScore;
		chr.aptitude = this.aptitude;
		
		for(double p : this.params)
			chr.params.add(new Double(p));
		
		chr.score = this.score;
		
		return chr;
	}

	public String toString() {
		String s = new String("");
		
		for (DoubleGene gen : this.genes) {
			s = s + gen;
			s += "|";
		}
		if(!s.isEmpty())
			s = s.substring(0, s.length() - 1);
		
		return s;
	}

}
