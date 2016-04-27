package model.geneticAlgorithm.mutation;

public class MutationFactory {
	private static MutationFactory instance;
	private static String[] strategies = {"Inversión", "Intercambio", "Inserción", "Heurística", "Propio"};
	
	private MutationFactory() {}
	
	public static MutationFactory getInstance() {
		if (instance == null){
			instance = new MutationFactory();
		}
		return instance;
	}
	
	public MutationInterface create(String id) {
		if (id == "Inversión")
			return new InversionMutation();
		else if (id == "Intercambio")
			return new ExchangeMutation();
		else if (id == "Inserción")
			return new InsertionMutation();
		else if (id == "Heurística")
			return new HeuristicMutation();
		else if (id == "Propio")
			return new OwnMutation();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
