package model.geneticAlgorithm.selection;

public class SelectionFactory {
	private static SelectionFactory instance;
	private static String[] strategies = {"ruleta", "torneo"};
 	
	public static SelectionFactory getInstance() {
		if (instance == null){
			instance = new SelectionFactory();
		}
		return instance;
	}
	
	public SelectionInterface create(String id) {
		if (id == "ruleta")
			return new RouletteSelection();
		else if (id == "torneo")
			return new TournamentSelection();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public static String[] selectionList() {
		return strategies;
	}
	
}
