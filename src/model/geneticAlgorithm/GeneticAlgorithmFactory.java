package model.geneticAlgorithm;

public class GeneticAlgorithmFactory {
	private static GeneticAlgorithmFactory instance;
 	
	public static GeneticAlgorithmFactory getInstance() {
		if (instance == null){
			instance = new GeneticAlgorithmFactory();
		}
		return instance;
	}
	
	public AbstractGeneticAlgorithm<?> create(String id) {
		if (id == "booleano")
			return new BooleanGeneticAlgorithm();
		else if (id == "real")
			return new DoubleGeneticAlgorithm();
		else
			throw new IllegalArgumentException("Unknown genetic algorithm");
	}
}
