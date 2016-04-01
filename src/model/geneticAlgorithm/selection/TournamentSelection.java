package model.geneticAlgorithm.selection;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;
import model.chromosome.comparator.ScoreComparator;

public class TournamentSelection implements SelectionInterface {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractChromosome<?>> void select(ArrayList<T> population, Random random) {
		ArrayList<T> selectedPopulation = new ArrayList<T>(population.size());
		int tournamentGroups = 2;
		ArrayList<T> group = new ArrayList<T>(tournamentGroups);
		
		for (int i = 0; i < population.size(); i += tournamentGroups) {
			group.clear();
			// take tournamentGroups individuals, or if less, the remaining ones
			for (int j = 0; j < tournamentGroups && (i + j) < population.size(); j++) {
				group.add(population.get(i + j));
			}
			
			group.sort(new ScoreComparator());
			
			T best = group.get(group.size() - 1);
			
			// put tournamentGroups copies of the best, or if less, just fill the population
			for (int j = 0; j < tournamentGroups && selectedPopulation.size() < population.size(); j++) {
				selectedPopulation.add((T) best.clone());
			}
		}
		
		population.clear();
		population.addAll(selectedPopulation);
	}

	@Override
	public String getName() {
		return "torneo";
	}

}
