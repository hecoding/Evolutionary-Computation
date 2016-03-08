package model.Chromosome;

import java.util.Comparator;

public class AptitudeComparator implements Comparator<BooleanChromosome> {

	@Override
	public int compare(BooleanChromosome o1, BooleanChromosome o2) {
		double aptitude = (o1.aptitude - o2.aptitude);
		
		if (aptitude > 0)
			return 1;
		else if (aptitude < 0)
			return -1;
		else return 0;
	}

}
