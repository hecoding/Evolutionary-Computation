package util;

import java.util.HashMap;

import model.chromosome.TSPChromosome;
import model.gene.AbstractGene;
import model.gene.IntegerGene;

public class RepeatedGene {
	
	public static boolean uniqueGenes(TSPChromosome tspChromosome) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for (AbstractGene<?> gene : tspChromosome.getGenotype()) {
			int geneInfo = ((IntegerGene) gene).getInformation().intValue();
			if(!map.containsKey(geneInfo))
				map.put(geneInfo, 1);
			else
				map.put(geneInfo, map.get(geneInfo) + 1);
		}
		
		for (Integer val : map.values()) {
			if(val != 1)
				return false;
		}
		
		return true;
	}
}
