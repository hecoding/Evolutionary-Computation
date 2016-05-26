package controller;

import java.util.ArrayList;

import model.function.Function;
import model.function.Function1;
import model.function.Function4;
import model.function.FunctionFactory;
import model.geneticAlgorithm.AbstractGeneticAlgorithm;
import model.geneticAlgorithm.BooleanGeneticAlgorithm;
import model.geneticAlgorithm.GeneticAlgorithmFactory;
import model.geneticAlgorithm.TransferGeneticAlgorithm;
import model.geneticAlgorithm.crossover.CrossoverFactory;
import model.geneticAlgorithm.crossover.CrossoverInterface;
import model.geneticAlgorithm.crossover.OnepointBitToBitCrossover;
import model.geneticAlgorithm.selection.RouletteSelection;
import model.geneticAlgorithm.selection.SelectionFactory;
import model.geneticAlgorithm.selection.SelectionInterface;
import model.observer.GeneticAlgorithmObserver;
import view.gui.swing.SettingsPanel.Check;
import view.gui.swing.SettingsPanel.Yes;
import view.gui.swing.SettingsPanel.No;
import view.gui.swing.SettingsPanel.Percentage;

public class Controller {
	private AbstractGeneticAlgorithm<?> ga;
	
	public Controller() {
		// default genetic algorithm
		this.ga = new BooleanGeneticAlgorithm(new Function1(), new RouletteSelection(), new OnepointBitToBitCrossover(), 100, false, 0.1, 100, 0.6, 0.05, 0.001, false, 0);
	}
	
	public void setParameters(TransferGeneticAlgorithm transfer) {
		ArrayList<GeneticAlgorithmObserver> observers = this.ga.getObservers();
		
		if(transfer.getRealChromosome().getOpcion())
			this.ga = GeneticAlgorithmFactory.getInstance().create("real");
		else
			this.ga = GeneticAlgorithmFactory.getInstance().create("boolean");
		
		Function function;
		if(transfer.getFunction() == "function 4")
			function = FunctionFactory.getInstance().createFunc4(transfer.getParamFunc4(), transfer.getRealChromosome().getOpcion());
		else
			function = FunctionFactory.getInstance().create(transfer.getFunction());
		SelectionInterface selection = SelectionFactory.getInstance().create(transfer.getSelection());
		CrossoverInterface crossover = CrossoverFactory.getInstance().create(transfer.getCrossover());
		double elitePercentage = 0;
		boolean elitism = transfer.getElitism().getOpcion();
		elitePercentage = transfer.getEliteRate().getPerc();
		
		this.ga.restart(function, selection, crossover, transfer.getPopulationSize(), elitism, elitePercentage,
				transfer.getGenerations(), transfer.getCrossoverProb(), transfer.getMutationProb(),
				transfer.getPrecision(), transfer.getCustomSeed().getOpcion(), transfer.getSeed());
		
		this.ga.setObservers(observers);
	}
	
	public TransferGeneticAlgorithm getParameters() {
		TransferGeneticAlgorithm transfer = new TransferGeneticAlgorithm();
		
		Check elitism;
		Percentage porcElite;
		Function function = this.ga.getFunction();
		SelectionInterface selection = this.ga.getSelectionStrategy();
		CrossoverInterface crossover = this.ga.getCrossoverStrategy();
		Check customSeed;
		
		transfer.setFunction(function.getName());
		transfer.setParamFunc4(function.paramNum());
		Check real = new No();
		if(function.getName() == "function 4" && ((Function4) function).isReal())
				real = new Yes();
		transfer.setRealChromosome(real);
		transfer.setSelection(selection.getName());
		transfer.setCrossover(crossover.getName());
		transfer.setPrecision(this.ga.getTolerance());
		transfer.setPopulationSize(this.ga.getPopulationNum());
		transfer.setGenerations(this.ga.getMaxGenerationNum());
		transfer.setCrossoverProb(this.ga.getCrossProb());
		transfer.setMutationProb(this.ga.getMutationProb());
		if(this.ga.isCustomSeed())
			customSeed = new Yes();
		else
			customSeed = new No();
		transfer.setCustomSeed(customSeed);
		transfer.setSeed((int) this.ga.getSeed());
		if (this.ga.isUseElitism())
			elitism = new Yes();
		else
			elitism = new No();
		transfer.setElitism(elitism);
		porcElite = new Percentage();
		porcElite.setPerc(this.ga.getElitePercentage());
		transfer.setEliteRate(porcElite);
		
		return transfer;
	}
	
	public void run() {
		this.ga.run();
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
