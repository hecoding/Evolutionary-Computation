package model.geneticAlgorithm.mutation;

public class MutationFactory {
	private static MutationFactory instance;
	private static String[] strategies = {"bit a bit", "uniforme"};
	
	private MutationFactory() {}
	
	public static MutationFactory getInstance() {
		if (instance == null){
			instance = new MutationFactory();
		}
		return instance;
	}
	
	public MutationInterface create(String id) {
		if (id == "bit a bit")
			return new BitFlipMutation();
		else if (id == "uniforme")
			return new UniformMutation();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}