package model.geneticAlgorithm.selection;

public class SelectionFactory {
	private static SelectionFactory instance;
	private static String[] strategies = {"Ruleta", "Torneo", "Truncamiento"};
	
	private SelectionFactory() {}
 	
	public static SelectionFactory getInstance() {
		if (instance == null){
			instance = new SelectionFactory();
		}
		return instance;
	}
	
	public SelectionInterface create(String id) {
		if (id == "Ruleta")
			return new RouletteSelection();
		else if (id == "Torneo")
			return new TournamentSelection();
		else if (id == "Truncamiento")
			return new TruncationSelection();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
	
}
