package controller;

import java.util.ArrayList;

import model.function.Function;
import model.function.Function1;
import model.function.Function4;
import model.function.FunctionFactory;
import model.geneticAlgorithm.AbstractGeneticAlgorithm;
import model.geneticAlgorithm.BooleanGeneticAlgorithm;
import model.geneticAlgorithm.GeneticAlgorithmFactory;
import model.geneticAlgorithm.crossover.CrossoverFactory;
import model.geneticAlgorithm.crossover.CrossoverInterface;
import model.geneticAlgorithm.crossover.OnepointBitToBitCrossover;
import model.geneticAlgorithm.selection.RouletteSelection;
import model.geneticAlgorithm.selection.SelectionFactory;
import model.geneticAlgorithm.selection.SelectionInterface;
import model.observer.GeneticAlgorithmObserver;

public class Controller {
	private AbstractGeneticAlgorithm<?> ga;
	
	public Controller() {
		// default genetic algorithm
		this.ga = new BooleanGeneticAlgorithm(new Function1(), new RouletteSelection(), new OnepointBitToBitCrossover(), 100, false, 0.1, 100, 0.6, 0.05, 0.001, false, 0);
	}
	
	/*public void setParameters(TransferGeneticAlgorithm transfer) {
		ArrayList<GeneticAlgorithmObserver> observers = this.ga.getObservers();
		
		if(transfer.getCromosomaReal().getOpcion())
			this.ga = GeneticAlgorithmFactory.getInstance().create("real");
		else
			this.ga = GeneticAlgorithmFactory.getInstance().create("booleano");
		
		Function function;
		if(transfer.getFuncion() == "función 4")
			function = FunctionFactory.getInstance().createFunc4(transfer.getParamFunc4(), transfer.getCromosomaReal().getOpcion());
		else
			function = FunctionFactory.getInstance().create(transfer.getFuncion());
		SelectionInterface selection = SelectionFactory.getInstance().create(transfer.getSeleccion());
		CrossoverInterface crossover = CrossoverFactory.getInstance().create(transfer.getCruce());
		double elitePercentage = 0;
		boolean elitism = transfer.getElitismo().getOpcion();
		elitePercentage = transfer.getPorcElite().getPerc();
		
		this.ga.restart(function, selection, crossover, transfer.getPoblacion(), elitism, elitePercentage,
				transfer.getGeneraciones(), transfer.getPorcCruces(), transfer.getPorcMutacion(),
				transfer.getPrecision(), transfer.getSemillaPersonalizada().getOpcion(), transfer.getSemilla());
		
		this.ga.setObservers(observers);
		// LAS PROBS PARECE QUE NO VAN
	}
	
	public TransferGeneticAlgorithm getParameters() {
		TransferGeneticAlgorithm transfer = new TransferGeneticAlgorithm();
		
		Check elitism;
		Percentage porcElite;
		Function funcion = this.ga.getFunction();
		SelectionInterface seleccion = this.ga.getSelectionStrategy();
		CrossoverInterface cruce = this.ga.getCrossoverStrategy();
		Check semillaPersonalizada;
		
		transfer.setFuncion(funcion.getName());
		transfer.setParamFunc4(funcion.paramNum());
		Check real = new No();
		if(funcion.getName() == "función 4" && ((Function4) funcion).isReal())
				real = new Si();
		transfer.setCromosomaReal(real);
		transfer.setSeleccion(seleccion.getName());
		transfer.setCruce(cruce.getName());
		transfer.setPrecision(this.ga.getTolerance());
		transfer.setPoblacion(this.ga.getPopulationNum());
		transfer.setGeneraciones(this.ga.getMaxGenerationNum());
		transfer.setPorcCruces(this.ga.getCrossProb());
		transfer.setPorcMutacion(this.ga.getMutationProb());
		if(this.ga.isCustomSeed())
			semillaPersonalizada = new Si();
		else
			semillaPersonalizada = new No();
		transfer.setSemillaPersonalizada(semillaPersonalizada);
		transfer.setSemilla((int) this.ga.getSeed());
		if (this.ga.isUseElitism())
			elitism = new Si();
		else
			elitism = new No();
		transfer.setElitismo(elitism);
		porcElite = new Percentage();
		porcElite.setPerc(this.ga.getElitePercentage());
		transfer.setPorcElite(porcElite);
		
		return transfer;
	}*/
	
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
