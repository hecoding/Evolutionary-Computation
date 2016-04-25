package controller;

import java.util.ArrayList;

import model.geneticAlgorithm.AbstractGeneticAlgorithm;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import model.geneticAlgorithm.crossover.CrossoverFactory;
import model.geneticAlgorithm.crossover.PMXCrossover;
import model.geneticAlgorithm.fitnessFunction.TSPFitness;
import model.geneticAlgorithm.mutation.InversionMutation;
import model.geneticAlgorithm.mutation.MutationFactory;
import model.geneticAlgorithm.selection.RouletteSelection;
import model.geneticAlgorithm.selection.SelectionFactory;
import model.observer.GeneticAlgorithmObserver;

public class Controller {
	private AbstractGeneticAlgorithm<?> ga;
	
	public Controller() {
		// default genetic algorithm
		this.ga = new TSPGeneticAlgorithm(
									new TSPFitness(),
									new RouletteSelection(),
									new PMXCrossover(),
									new InversionMutation(),
									100,
									false,
									0.1,
									100,
									0.6,
									0.05);
	}
	
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
	
	public void setSelectionParameter(String param) {
		if(!param.isEmpty()) {
			double num = Double.parseDouble(param);
			if(num <= 0)
				throw new IllegalArgumentException("Mal parámetro");
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
	
	public String getSelectionStrategy() {
		return this.ga.getSelectionStrategy().getName();
	}
	
	public String[] getSelectionStrategyList() {
		return SelectionFactory.selectionList();
	}
	
	public String getCrossoverStrategy() {
		return this.ga.getCrossoverStrategy().getName();
	}
	
	public String[] getCrossoverStrategyList() {
		return CrossoverFactory.selectionList();
	}
	
	public String getMutationStrategy() {
		return this.ga.getMutationStrategy().getName();
	}
	
	public String[] getMutationStrategyList() {
		return MutationFactory.selectionList();
	}
	
	public double getFunctionResult() {
		return this.ga.getBestChromosome().getAptitude();
	}
	
	public ArrayList<Double> getResult() {
		return this.ga.getBestChromosome().getPhenotype();
	}
	
	public boolean getVariableMutation() {
		return this.ga.isVariableMutation();
	}
	
	public void setVariableMutation(boolean b) {
		this.ga.setVariableMutation(b);
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
	
	private static double[] toPrimitiveArray(ArrayList<Double> a) {
		return a.stream().mapToDouble(d -> d).toArray();
	}
	
	public void addModelObserver(GeneticAlgorithmObserver o) {
		this.ga.addObserver(o);
	}
}
