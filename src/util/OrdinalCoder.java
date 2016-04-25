package util;

import java.util.ArrayList;
import java.util.LinkedList;

import model.chromosome.TSPChromosome;
import model.gene.IntegerGene;

public class OrdinalCoder {
	
	public static TSPChromosome deOrdinalize(TSPChromosome ordinal) {
		ArrayList<IntegerGene> genes = ordinal.getGenotype();
		TSPChromosome chrom = new TSPChromosome();
		if(genes == null)
			return chrom;
		ArrayList<IntegerGene> ph = new ArrayList<IntegerGene>(genes.size());
		
		LinkedList<Integer> dynamicList = new LinkedList<Integer>();
		for (int i = 0; i < genes.size(); i++) {
			dynamicList.add(i);
		}
		
		for (IntegerGene gene : genes) {
			ph.add( new IntegerGene( dynamicList.remove( gene.getInformation().intValue() ) ));
		}
		
		chrom.setGenotype(ph);
		
		return chrom;
	}
	
	public static TSPChromosome ordinalize(TSPChromosome chrom) {
		ArrayList<IntegerGene> genes = chrom.getGenotype();
		TSPChromosome ordinal = new TSPChromosome();
		if(genes == null)
			return ordinal;
		ArrayList<IntegerGene> ordinalGenes = new ArrayList<IntegerGene>(genes.size());
		LinkedList<Integer> dynamicList = new LinkedList<Integer>();
		for (int i = 0; i < genes.size(); i++) {
			dynamicList.add(i);
		}
		
		for (IntegerGene gene : genes) {
			int idx = dynamicList.indexOf(gene.getInformation());
			ordinalGenes.add(new IntegerGene(idx));
			dynamicList.remove(idx);
		}
		
		ordinal.setGenotype(ordinalGenes);
		
		return ordinal;
	}

}