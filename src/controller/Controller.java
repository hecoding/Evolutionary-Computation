package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Ant;
import model.Map;
import model.chromosome.AntTrailChromosome;
import model.genProgAlgorithm.AbstractGeneticAlgorithm;
import model.genProgAlgorithm.AntTrailGeneticAlgorithm;
import model.genProgAlgorithm.crossover.CrossoverFactory;
import model.genProgAlgorithm.crossover.CrossoverInterface;
import model.genProgAlgorithm.crossover.OnePointCrossover;
import model.genProgAlgorithm.fitnessFunction.AntTrailFitness;
import model.genProgAlgorithm.initialization.InitializationFactory;
import model.genProgAlgorithm.initialization.InitializationInterface;
import model.genProgAlgorithm.mutation.MutationFactory;
import model.genProgAlgorithm.mutation.MutationInterface;
import model.genProgAlgorithm.mutation.SimpleTerminalMutation;
import model.genProgAlgorithm.selection.RouletteSelection;
import model.genProgAlgorithm.selection.SelectionFactory;
import model.genProgAlgorithm.selection.SelectionInterface;
import model.observer.GeneticAlgorithmObserver;
import util.MapParser;

public class Controller {
	private AbstractGeneticAlgorithm<?> ga;
	private boolean rangeParameters;
	private double minRange, maxRange, step;
	private String param;
	private ArrayList<Double> results;
	private boolean finish;
	
	public Controller() {
		this.finish = true;
		Map map;
		try {
			map = MapParser.parse("src/map.txt", 89);
		} catch (IOException e) {
			System.err.println("Map not found");
			map = null;
		}
		
		// default genetic algorithm
		this.ga = new AntTrailGeneticAlgorithm(
									map,
									null,
									new AntTrailFitness(),
									new RouletteSelection(),
									new OnePointCrossover(),
									new SimpleTerminalMutation(),
									400,
									false,
									0,
									500,
									4,
									0.6,
									0.05);
		this.rangeParameters = false;
		this.results = new ArrayList<Double>();
	}
	
	public void run() {
		this.ga.reset();
		if (!this.rangeParameters)
			this.ga.run();
		else {
			this.finish = false;
			this.results.clear();
			ArrayList<Double> range = createRange(this.minRange, this.maxRange, this.step);
			for (Double num : range) {
				this.ga.reset();
				this.setCurrentParam(num);
				this.ga.run();
				this.results.add( this.getFunctionResult() );
			}
			this.finish = true;
		}
	}
	
	private ArrayList<Double> createRange(double min, double max, double step) {
		boolean end = false;
		double current = min;
		ArrayList<Double> range = new ArrayList<Double>();
		range.add(current);
		
		while(!end) {
			current += step;
			if(current > max)
				end = true;
			else
				range.add(current);
		}
		
		return range;
	}
	
	private void setCurrentParam(double num) {
		switch(this.param) {
		case "population":
			this.ga.setPopulation((int) Math.round(num));
			break;
		case "generations":
			this.ga.setMaxGenerations((int) Math.round(num));
			break;
		case "crossover":
			this.ga.setCrossProb(num);
			break;
		case "mutation":
			this.ga.setMutationProb(num);
			break;
		case "elitism":
			this.ga.setElitePercentage((int) Math.round(num * 100));
			break;
		}
	}
	
	public int getPopulation() {
		return this.ga.getPopulationNum();
	}
	
	public void setPopulation(int population) {
		this.ga.setPopulation(population);
	}
	
	public int getGenerations() {
		return this.ga.getMaxGenerationNum();
	}
	
	public void setGenerations(int generations) {
		this.ga.setMaxGenerations(generations);
	}
	
	public int getHeight() {
		return this.ga.getProgramHeight();
	}
	
	public void setHeight(int height) {
		this.ga.setProgramHeight(height);
	}
	
	public int getTournamentSelectionGroups() {
		return (int) SelectionFactory.getInstance().getParameter();
	}
	
	public int getCrossoverPercentage() {
		return (int) (this.ga.getCrossProb() * 100);
	}
	
	public void setCrossoverPercentage(int perc) {
		this.ga.setCrossProb((double) perc / 100);
	}
	
	public int getMutationPercentage() {
		return (int) (this.ga.getMutationProb() * 100);
	}
	
	public void setMutationPercentage(int perc) {
		this.ga.setMutationProb((double) perc / 100);
	}
	
	public int getElitismPercentage() {
		return (int) (this.ga.getElitePercentage() * 100);
	}
	
	public void setElitismPercentage(int perc) {
		if(perc == 0)
			this.ga.useElitism(false);
		else
			this.ga.useElitism(true);
		this.ga.setElitePercentage(perc);
	}
	
	public void setInitializationStrategy(String strategy) {
		this.ga.setInitializationStrategy(InitializationFactory.getInstance().create(strategy));
	}
	
	public void setSelectionStrategy(String strategy) {
		this.ga.setSelectionStrategy(SelectionFactory.getInstance().create(strategy));
	}
	
	public void setSelectionParameter(String param) {
		if(!param.isEmpty()) {
			double num = Double.parseDouble(param);
			if(num <= 0)
				throw new IllegalArgumentException("Bad parameter");
			else
				SelectionFactory.getInstance().setParameter(num);
		}
	}
	
	public void setCrossoverStrategy(String strategy) {
		this.ga.setCrossoverStrategy(CrossoverFactory.getInstance().create(strategy));
	}
	
	public void setMutationStrategy(String strategy) {
		this.ga.setMutationStrategy(MutationFactory.getInstance().create(strategy));
	}
	
	public String getinitializationStrategy() {
		InitializationInterface strat = this.ga.getInitializationStrategy();
		
		if(strat == null) return "";
		else return strat.getName();
	}

	public String[] getInitializationStrategyList() {
		return InitializationFactory.selectionList();
	}
	
	public String getSelectionStrategy() {
		SelectionInterface strat = this.ga.getSelectionStrategy();
		
		if(strat == null) return "";
		else return strat.getName();
	}
	
	public String[] getSelectionStrategyList() {
		return SelectionFactory.selectionList();
	}
	
	public String getCrossoverStrategy() {
		CrossoverInterface strat = this.ga.getCrossoverStrategy();
		
		if(strat == null) return "";
		else return strat.getName();
	}
	
	public String[] getCrossoverStrategyList() {
		return CrossoverFactory.selectionList();
	}
	
	public String getMutationStrategy() {
		MutationInterface strat = this.ga.getMutationStrategy();
		
		if(strat == null) return "";
		else return strat.getName();
	}
	
	public String[] getMutationStrategyList() {
		return MutationFactory.selectionList();
	}
	
	public void setRangeParameters(boolean b) {
		this.rangeParameters = b;
	}
	
	public boolean isRangeParameters() {
		return this.rangeParameters;
	}
	
	public void setRanges(double min, double max, double step) {
		this.minRange = min;
		this.maxRange = max;
		this.step = step;
	}
	
	public void setParamRange(String s) {
		this.param = s;
	}
	
	public double getFunctionResult() {
		return this.ga.getBestChromosome().getAptitude();
	}
	
	public ArrayList<Double> getRangeResults() {
		return this.results;
	}
	
	public String getResult() {
		return this.ga.getBestChromosome().getPhenotype();
	}
	
	public Map getResultMap() {
		Map m = AntTrailGeneticAlgorithm.getMap();
		Ant a = new Ant(m.getColumns(), m.getRows());
		AntTrailChromosome.runProgram(((AntTrailChromosome) this.ga.getBestChromosome()).getProgram(), m, a);
		
		return m;
	}
	
	public boolean isMinimization() {
		return this.ga.getFitnessFunction().isMinimization();
	}
	
	public boolean isFinished() {
		return this.finish;
	}
	
	public boolean isContentBasedTermination() {
		return this.ga.isContentBasedTermination();
	}
	
	public void setContentBasedTermination(boolean contentBasedTermination) {
		this.ga.setContentBasedTermination(contentBasedTermination);
	}
	
	public double[] getBestChromosomeList() {
		return toPrimitiveArray(this.ga.getBestChromosomeList());
	}
	
	public double[] getBestAptitudeList() {
		return toPrimitiveArray(this.ga.getBestAptitudeList());
	}
	
	public double[] getAverageAptitudeList() {
		return toPrimitiveArray(this.ga.getAverageAptitudeList());
	}
	
	public double[] getRangeList() {
		return toPrimitiveArray(createRange(this.minRange, this.maxRange, this.step));
	}
	
	public double[] getResultsList() {
		return toPrimitiveArray(this.results);
	}
	
	private static double[] toPrimitiveArray(ArrayList<Double> a) {
		return a.stream().mapToDouble(d -> d).toArray();
	}
	
	public void addModelObserver(GeneticAlgorithmObserver o) {
		this.ga.addObserver(o);
	}
	
}
