package model.chromosome;

import java.util.ArrayList;

import model.gene.IntegerGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import model.geneticAlgorithm.fitnessFunction.FitnessFunctionInterface;
import util.RandGenerator;

public class TSPChromosome extends AbstractChromosome<IntegerGene> {
	
	public TSPChromosome() {
		this.genes = new ArrayList<IntegerGene>();
	}
	
	public TSPChromosome(int geneNumber, FitnessFunctionInterface function) {
		fitnessFunc = function;
		this.genes = new ArrayList<IntegerGene>(geneNumber);
	}

	@Override
	public void initialize() {
		if (!this.genes.isEmpty())
			this.genes.clear();
		
		RandGenerator random = RandGenerator.getInstance();
		int i = 0;
		while (i < TSPGeneticAlgorithm.Cities.number) {
			IntegerGene newGene = new IntegerGene( random.nextInt(TSPGeneticAlgorithm.Cities.number) );
			if(!this.genes.contains(newGene)) { // Overrode .equals()
				this.genes.add( newGene );
				i++;
			}
		}
	}

	@Override
	public ArrayList<Double> getPhenotype() {
		ArrayList<Double> ph = new ArrayList<Double>(this.genes.size());
		
		for (IntegerGene gene : this.genes) {
			ph.add((double) gene.getInformation());
		}
		
		return ph;
	}

	@Override
	public TSPChromosome clone() {
		// deep copy indeed
		TSPChromosome chr = new TSPChromosome();
		ArrayList<IntegerGene> clonedList = new ArrayList<IntegerGene>(genes.size());
		for (IntegerGene gene : genes) {
			clonedList.add(gene.clone());
		}
		
		chr.setGenotype(clonedList);
		chr.aggregateScore = this.aggregateScore;
		chr.aptitude = this.aptitude;
		chr.score = this.score;
		
		return chr;
	}

	@Override
	public String toString() {
		String s = new String("");
		String separator = new String(" - ");
		
		for (IntegerGene gen : this.genes) {
			s = s + gen;
			s += separator;
		}
		if(!s.isEmpty())
			s = s.substring(0, s.length() - separator.length());
		
		return s;
	}

}
