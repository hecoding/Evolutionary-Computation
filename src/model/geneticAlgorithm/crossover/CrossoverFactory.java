package model.geneticAlgorithm.crossover;

public class CrossoverFactory {
	private static CrossoverFactory instance;
	private static String[] strategies = {"PMX", "OX", "Pos OX", "CX", "ERX", "Ordinal", "Propio"};
	
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
		else if (id == "Pos OX")
			return new PositionBasedCrossover();
		else if (id == "ERX")
			return new ERXCrossover();
		else if (id == "Ordinal")
			return new OrdinalCrossover();
		else if (id == "Propio")
			return new OwnCrossover();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
