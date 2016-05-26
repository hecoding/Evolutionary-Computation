package model.geneticAlgorithm;

import view.gui.swing.SettingsPanel.Check;
import view.gui.swing.SettingsPanel.Percentage;

public class TransferGeneticAlgorithm {
	private String function;
	private String selection;
	private String crossover;
	private Double precision;
	private int populationSize;
	private int generations;
	private double crossoverProb;
	private double mutationProb;
	private Check customSeed;
	private int seed;
	private Check elitism;
	private Percentage eliteRate;
	private int paramFunc4 = 1;
	private Check realChromosome;
	
	public String getFunction() {
		return function;
	}

	public void setFunction(String funcion) {
		this.function = funcion;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String seleccion) {
		this.selection = seleccion;
	}

	public String getCrossover() {
		return crossover;
	}

	public void setCrossover(String cruce) {
		this.crossover = cruce;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int poblacion) {
		this.populationSize = poblacion;
	}

	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public double getCrossoverProb() {
		return crossoverProb;
	}

	public void setCrossoverProb(double prob) {
		this.crossoverProb = prob;
	}

	public double getMutationProb() {
		return mutationProb;
	}

	public void setMutationProb(double prob) {
		this.mutationProb = prob;
	}

	public Check getCustomSeed() {
		return customSeed;
	}

	public void setCustomSeed(Check seed) {
		this.customSeed = seed;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public Check getElitism() {
		return elitism;
	}

	public void setElitism(Check elitism) {
		this.elitism = elitism;
	}

	public Percentage getEliteRate() {
		return eliteRate;
	}

	public void setEliteRate(Percentage rate) {
		this.eliteRate = rate;
	}

	public int getParamFunc4() {
		return paramFunc4;
	}

	public void setParamFunc4(int paramFunc4) {
		this.paramFunc4 = paramFunc4;
	}

	public Check getRealChromosome() {
		return realChromosome;
	}

	public void setRealChromosome(Check cromosomaReal) {
		this.realChromosome = cromosomaReal;
	}

	public String toString() {
		return "function: " + this.function + System.lineSeparator() +
				"selection: " + this.selection + System.lineSeparator() +
				"crossover: " + this.crossover + System.lineSeparator() +
				"param func 4: " + this.paramFunc4 + System.lineSeparator() +
				"real chrom: " + this.realChromosome + System.lineSeparator() +
				"precision: " + this.precision + System.lineSeparator() +
				"population size: " + this.populationSize + System.lineSeparator() +
				"generations: " + this.generations + System.lineSeparator() +
				"crossover prob: " + this.crossoverProb + System.lineSeparator() +
				"mutation prob: " + this.mutationProb + System.lineSeparator() +
				"custom seed: " + this.customSeed + System.lineSeparator() +
				"seed: " + this.seed + System.lineSeparator() +
				"elitism: " + this.elitism + System.lineSeparator() +
				"elitism rate: " + this.eliteRate.getPerc();
	}
}
