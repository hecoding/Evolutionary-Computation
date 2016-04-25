package util;

import model.chromosome.TSPChromosome;
import model.gene.IntegerGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;

public class OrdinalCheck {
	public static boolean check(TSPChromosome tspChromosome) {
		
		int i = 0;
		for (IntegerGene gene : tspChromosome.getGenotype()) {
			int tal = TSPGeneticAlgorithm.Cities.number - i;
			if (gene.getInformation().intValue() >= tal)
				return false;
			i++;
		}
		
		return true;
	}
}