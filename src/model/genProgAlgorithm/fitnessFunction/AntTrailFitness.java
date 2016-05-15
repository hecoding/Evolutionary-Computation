package model.genProgAlgorithm.fitnessFunction;

import java.util.ArrayList;

public class AntTrailFitness implements FitnessFunctionInterface {

	@Override
	public double f(ArrayList<Double> params) {
		return 0;
	}

	@Override
	public boolean isMinimization() {
		return true;
	}

	@Override
	public String getName() {
		return "Ant Trail Fitness";
	}

}
