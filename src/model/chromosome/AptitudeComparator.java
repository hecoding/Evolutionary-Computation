package model.chromosome;

import java.util.Comparator;

public class AptitudeComparator implements Comparator<AbstractChromosome> {

	@Override
	public int compare(AbstractChromosome o1, AbstractChromosome o2) {
		double aptitude = (o1.aptitude - o2.aptitude);
		
		if (aptitude > 0)
			return 1;
		else if (aptitude < 0)
			return -1;
		else return 0;
	}

}
