package model.chromosome;

import java.util.Comparator;

import model.gene.AbstractGene;

public class AptitudeComparator implements Comparator<AbstractChromosome<? extends AbstractGene<?>>> {

	@Override
	public int compare(AbstractChromosome<?> o1, AbstractChromosome<?> o2) {
		double aptitude = (o1.getAptitude() - o2.getAptitude());
		
		if (aptitude > 0)
			return 1;
		else if (aptitude < 0)
			return -1;
		else return 0;
	}

}
