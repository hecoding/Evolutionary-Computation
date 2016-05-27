package model.geneticAlgorithm.mutation;

public class MutationFactory {
	private static MutationFactory instance;
	private static String[] strategies = {"Inversion", "Exchange", "Insertion", "Heuristic", "Own"};
	
	private MutationFactory() {}
	
	public static MutationFactory getInstance() {
		if (instance == null){
			instance = new MutationFactory();
		}
		return instance;
	}
	
	public MutationInterface create(String id) {
		if (id == "Inversion")
			return new InversionMutation();
		else if (id == "Exchange")
			return new ExchangeMutation();
		else if (id == "Insertion")
			return new InsertionMutation();
		else if (id == "Heuristic")
			return new HeuristicMutation();
		else if (id == "Own")
			return new OwnMutation();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
