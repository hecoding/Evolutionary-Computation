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
		if (id == "TSP")
			return new TSPGeneticAlgorithm();
		else
			throw new IllegalArgumentException("Unknown genetic algorithm");
	}
}
