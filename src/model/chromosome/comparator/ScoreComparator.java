package model.chromosome.comparator;

import java.util.Comparator;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;

public class ScoreComparator implements Comparator<AbstractChromosome<? extends AbstractGene<?>>> {

	@Override
	public int compare(AbstractChromosome<?> o1, AbstractChromosome<?> o2) {
		double score = (o1.getScore() - o2.getScore());
		
		if (score > 0)
			return 1;
		else if (score < 0)
			return -1;
		else return 0;
	}

}
