package model.geneticAlgorithm.crossover;

public class CrossoverFactory {
	private static CrossoverFactory instance;
	private static String[] strategies = {"hacer"};
	
	private CrossoverFactory() {}
 	
	public static CrossoverFactory getInstance() {
		if (instance == null){
			instance = new CrossoverFactory();
		}
		return instance;
	}
	
	public CrossoverInterface create(String id) {
		if (id == "un punto bit a bit")
			return null;
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
