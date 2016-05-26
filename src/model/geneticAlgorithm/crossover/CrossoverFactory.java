package model.geneticAlgorithm.crossover;

public class CrossoverFactory {
private static CrossoverFactory instance;
 	
	public static CrossoverFactory getInstance() {
		if (instance == null){
			instance = new CrossoverFactory();
		}
		return instance;
	}
	
	public CrossoverInterface create(String id) {
		if (id == "one-point bit-to-bit")
			return new OnepointBitToBitCrossover();
		else if (id == "one-point")
			return new OnepointCrossover();
		else if (id == "uniform")
			return new UniformCrossover();
		else if (id == "arithmetic")
			return new ArithmeticCrossover();
		else if (id == "SBX")
			return new SimulatedBinaryCrossover();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
}
