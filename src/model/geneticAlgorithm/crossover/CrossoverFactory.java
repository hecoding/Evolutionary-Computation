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
		if (id == "un punto bit")
			return new OnepointBitToBitCrossover();
		else if (id == "un punto")
			return new OnepointCrossover();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
}
