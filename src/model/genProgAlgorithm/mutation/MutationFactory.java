package model.genProgAlgorithm.mutation;

public class MutationFactory {
	private static MutationFactory instance;
	private static String[] strategies = {};
	
	private MutationFactory() {}
	
	public static MutationFactory getInstance() {
		if (instance == null){
			instance = new MutationFactory();
		}
		return instance;
	}
	
	public MutationInterface create(String id) {
		if (id == "Inversión")
			return null;
		//else
			//throw new IllegalArgumentException("Unknown mutation method");
		return null;
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
