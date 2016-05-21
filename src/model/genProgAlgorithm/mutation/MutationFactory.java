package model.genProgAlgorithm.mutation;

public class MutationFactory {
	private static MutationFactory instance;
	private static String[] strategies = {"Terminal smp.", "Funcional smp."};
	
	private MutationFactory() {}
	
	public static MutationFactory getInstance() {
		if (instance == null){
			instance = new MutationFactory();
		}
		return instance;
	}
	
	public MutationInterface create(String id) {
		if (id == "Terminal smp.")
			return new SimpleTerminalMutation();
		else if (id == "Funcional smp.")
			return new SimpleFunctionalMutation();
		else
			throw new IllegalArgumentException("Unknown mutation method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
