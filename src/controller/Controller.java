package controller;

import java.util.ArrayList;

import model.function.Function3;
import model.geneticAlgorithm.AbstractGeneticAlgorithm;
import model.geneticAlgorithm.BooleanGeneticAlgorithm;
import model.geneticAlgorithm.crossover.CrossoverFactory;
import model.geneticAlgorithm.crossover.OnepointBitToBitCrossover;
import model.geneticAlgorithm.selection.RouletteSelection;
import model.geneticAlgorithm.selection.SelectionFactory;
import model.observer.GeneticAlgorithmObserver;

public class Controller {
	private AbstractGeneticAlgorithm<?> ga;
	
	public Controller() {
		// default genetic algorithm
		this.ga = new BooleanGeneticAlgorithm(
									new Function3(),
									new RouletteSelection(),
									new OnepointBitToBitCrossover(),
									100,
									false,
									0.1,
									100,
									0.6,
									0.05,
									0.001,
									false,
									0);
	}
		// LAS PROBS PARECE QUE NO VAN
	
	public void run() {
		this.ga.restart();
		this.ga.run();
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
		this.ga.setElitePercentage(perc);
	}
	
	public boolean getElitism() {
		return this.ga.isUseElitism();
	}
	
	public void setElitism(boolean elitism) {
		this.ga.useElitism(elitism);
	}
	
	public void setSelectionStrategy(String strategy) {
		this.ga.setSelectionStrategy(SelectionFactory.getInstance().create(strategy));
	}
	
	public void setCrossoverStrategy(String strategy) {
		this.ga.setCrossoverStrategy(CrossoverFactory.getInstance().create(strategy));
	}
	
	public void setMutationStrategy(String strategy) {
		throw new IllegalArgumentException();
	}
	
	public String[] getSelectionStrategyList() {
		return SelectionFactory.selectionList();
	}
	
	public String[] getCrossoverStrategyList() {
		return CrossoverFactory.selectionList();
	}
	
	public String[] getMutationStrategyList() {
		return null;
	}
	
	public double getFunctionResult() {
		return this.ga.getBestChromosome().getAptitude();
	}
	
	public ArrayList<Double> getResult() {
		return this.ga.getBestChromosome().getPhenotype();
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
	
	private static double[] toPrimitiveArray(ArrayList<Double> a) {
		return a.stream().mapToDouble(d -> d).toArray();
	}
	
	public void addModelObserver(GeneticAlgorithmObserver o) {
		this.ga.addObserver(o);
	}
}
