package model.genProgAlgorithm.crossover;

public class CrossoverFactory {
	private static CrossoverFactory instance;
	private static String[] strategies = {"Un punto", "Dos puntos"};
	
	private CrossoverFactory() {}
 	
	public static CrossoverFactory getInstance() {
		if (instance == null){
			instance = new CrossoverFactory();
		}
		return instance;
	}
	
	public CrossoverInterface create(String id) {
		if (id == "Un punto")
			return new OnePointCrossover();
		else if(id == "Dos puntos")
			return new TwoPointCrossover();
		else
			throw new IllegalArgumentException("Unknown crossover method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
