package model.geneticAlgorithm.fitnessFunction;

import java.util.ArrayList;

import model.geneticAlgorithm.TSPGeneticAlgorithm;

public class TSPFitness implements FitnessFunctionInterface {

	@Override
	public double f(ArrayList<Double> params) {
		int distance = 0;
		
		for (int i = 0; i < params.size() - 1; i++) {
			int city1 = params.get(i).intValue();
			int city2 = params.get(i + 1).intValue();
			
			distance += TSPGeneticAlgorithm.Cities.distance(city1, city2);
		}
		
		return distance;
	}

	@Override
	public boolean isMinimization() {
		return true;
	}

	@Override
	public String getName() {
		return "TSP Fitness";
	}

}
