package model.geneticAlgorithm.crossover;

public class CrossoverFactory {
	private static CrossoverFactory instance;
	private static String[] strategies = {"PMX", "OX", "CX", "ERX"};
	
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
		else if (id == "OX")
			return new OXCrossover();
		else if (id == "CX")
			return new CXCrossover();
		else if (id == "ERX")
			return new ERXCrossover();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
