package model.function;

import java.util.ArrayList;

public interface FitnessFunctionInterface {
	public double f(ArrayList<Double> params);
	public boolean isMinimization();
	public String getName();
}
