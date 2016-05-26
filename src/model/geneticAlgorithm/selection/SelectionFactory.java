package model.geneticAlgorithm.selection;

public class SelectionFactory {
	private static SelectionFactory instance;
	private static String[] strategies = {"Ruleta", "Torneo", "Truncamiento", "Ranking"};
	private double parameter = 2;
	
	private SelectionFactory() {}
 	
	public static SelectionFactory getInstance() {
		if (instance == null){
			instance = new SelectionFactory();
		}
		return instance;
	}
	
	public SelectionInterface create(String id) {
		if (id == "roulette")
			return new RouletteSelection();
		else if (id == "tournament")
			return new TournamentSelection((int) parameter);
		else if (id == "Truncamiento")
			return new TruncationSelection();
		else if (id == "Ranking")
			return new LinearRankSelection();
		else
			throw new IllegalArgumentException("Unknown selection method");
	}
	
	public double getParameter() {
		return this.parameter;
	}
	
	public void setParameter(double param) {
		this.parameter = param;
	}
	
	public static String[] selectionList() {
		return strategies;
	}
	
}
