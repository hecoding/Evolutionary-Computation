package model.genProgAlgorithm.crossover;

public class CrossoverFactory {
	private static CrossoverFactory instance;
	private static String[] strategies = {};
	
	private CrossoverFactory() {}
 	
	public static CrossoverFactory getInstance() {
		if (instance == null){
			instance = new CrossoverFactory();
		}
		return instance;
	}
	
	public CrossoverInterface create(String id) {
		if (id == "PMX")
			return null;
		//else
			//throw new IllegalArgumentException("Unknown crossover method");
		return null;
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
