package model.geneticAlgorithm.crossover;

public class CrossoverFactory {
	private static CrossoverFactory instance;
	private static String[] strategies = {"un punto bit a bit", "un punto", "discreto uniforme", "aritmético", "SBX"};
 	
	public static CrossoverFactory getInstance() {
		if (instance == null){
			instance = new CrossoverFactory();
		}
		return instance;
	}
	
	public CrossoverInterface create(String id) {
		if (id == "un punto bit a bit")
			return new OnepointBitToBitCrossover();
		else if (id == "un punto")
			return new OnepointCrossover();
		else if (id == "discreto uniforme")
			return new UniformCrossover();
		else if (id == "aritmético")
			return new ArithmeticCrossover();
		else if (id == "SBX")
			return new SimulatedBinaryCrossover();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
