package model.geneticAlgorithm.crossover;

public class CrossoverFactory {
	private static CrossoverFactory instance;
	private static String[] strategies = {"PMX"};
	
	private CrossoverFactory() {}
 	
	public static CrossoverFactory getInstance() {
		if (instance == null){
			instance = new CrossoverFactory();
		}
		return instance;
	}
	
	public CrossoverInterface create(String id) {
		if (id == "PMX")
			return new PMXCrossover();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
