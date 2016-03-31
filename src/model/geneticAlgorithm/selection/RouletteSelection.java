package model.geneticAlgorithm.selection;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;

public class RouletteSelection implements SelectionInterface {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractChromosome<?>> void select(ArrayList<T> population, Random random) {
		ArrayList<T> selectedPopulation = new ArrayList<T>(population.size());
		double prob = 0;
		int positionSelected = 0;
		
		for (int i = 0; i < population.size(); i++) {
			prob = random.nextDouble();
			positionSelected = 0;
			
			while((positionSelected < population.size()) &&
					(prob > population.get(positionSelected).getAggregateScore()))
				positionSelected++;
			
			selectedPopulation.add((T) population.get(positionSelected).clone());
		}
		
		population.clear();
		population.addAll(selectedPopulation);
	}

	@Override
	public String getName() {
		return "ruleta";
	}

}
