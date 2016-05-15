package model.genProgAlgorithm.initialization;

public class InitializationFactory {
	private static InitializationFactory instance;
	private static String[] strategies = {};
	
	private InitializationFactory() {}
	
	public static InitializationFactory getInstance() {
		if (instance == null){
			instance = new InitializationFactory();
		}
		return instance;
	}
	
	public InitializationInterface create(String id) {
		if (id == "Inversión")
			return null;
		else
			throw new IllegalArgumentException("Unknown initialization method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
}
