package model.geneticAlgorithm.mutation;

public class MutationFactory {
	private static MutationFactory instance;
	private static String[] strategies = {"hacer"};
	
	private MutationFactory() {}
	
	public static MutationFactory getInstance() {
		if (instance == null){
			instance = new MutationFactory();
		}
		return instance;
	}
	
	public MutationInterface create(String id) {
		if (id == "bit a bit")
			return null;
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
