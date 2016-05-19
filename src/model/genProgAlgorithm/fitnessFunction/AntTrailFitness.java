package model.genProgAlgorithm.fitnessFunction;

import java.util.ArrayList;

public class AntTrailFitness implements FitnessFunctionInterface {

	@Override
	public double f(ArrayList<Double> params) {
		int foodEaten = params.get(0).intValue();
		int steps = params.get(1).intValue();
		int maxSteps = params.get(2).intValue();
		
		return foodEaten + (1 - (steps / maxSteps)) * 20;
	}

	@Override
	public boolean isMinimization() {
		return false;
	}

	@Override
	public String getName() {
		return "Ant Trail Fitness";
	}

}
