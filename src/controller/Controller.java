package controller;

import java.util.ArrayList;

import model.function.Function;
import model.function.Function1;
import model.function.Function4;
import model.function.FunctionFactory;
import model.geneticAlgorithm.AbstractGeneticAlgorithm;
import model.geneticAlgorithm.BooleanGeneticAlgorithm;
import model.geneticAlgorithm.DoubleGeneticAlgorithm;
import model.geneticAlgorithm.TransferGeneticAlgorithm;
import model.observer.GeneticAlgorithmObserver;
import view.gui.swing.SettingsPanel.Check;
import view.gui.swing.SettingsPanel.Si;
import view.gui.swing.SettingsPanel.No;
import view.gui.swing.SettingsPanel.Percentage;

public class Controller {
	private AbstractGeneticAlgorithm<?> ga;
	private BooleanGeneticAlgorithm ba = new BooleanGeneticAlgorithm(new Function1(), 100, false, 0.1, 100, 0.6, 0.05, 0.001, false, 0);
	private DoubleGeneticAlgorithm da = new DoubleGeneticAlgorithm(new Function1(), 100, false, 0.1, 100, 0.6, 0.05, 0.001, false, 0);
	
	public Controller() {
	}
	
	public void setParameters(TransferGeneticAlgorithm transfer) {
		Function function;
		if(transfer.getFuncion() == "función 4")
			function = FunctionFactory.getInstance().createFunc4(transfer.getParamFunc4(), transfer.getCromosomaReal().getOpcion());
		else
			function = FunctionFactory.getInstance().create(transfer.getFuncion());
		double elitePercentage = 0;
		boolean elitism = transfer.getElitismo().getOpcion();
		elitePercentage = transfer.getPorcElite().getPerc();
		
		this.ba.restart(function, transfer.getPoblacion(), elitism, elitePercentage,
				transfer.getGeneraciones(), transfer.getPorcCruces(), transfer.getPorcMutacion(),
				transfer.getPrecision(), transfer.getSemillaPersonalizada().getOpcion(), transfer.getSemilla());
		this.da.restart(function, transfer.getPoblacion(), elitism, elitePercentage,
				transfer.getGeneraciones(), transfer.getPorcCruces(), transfer.getPorcMutacion(),
				transfer.getPrecision(), transfer.getSemillaPersonalizada().getOpcion(), transfer.getSemilla());
		
		this.ga = ba;
		if(transfer.getFuncion() == "función 4" && transfer.getCromosomaReal().getOpcion()) {
				this.ga = da;
		}
	}
	
	public TransferGeneticAlgorithm getParameters() {
		TransferGeneticAlgorithm transfer = new TransferGeneticAlgorithm();
		this.ga = this.ba; // por poner uno
		Check elitism;
		Percentage porcElite;
		Function funcion = this.ga.getFunction();
		Check semillaPersonalizada;
		
		transfer.setFuncion(funcion.getName());
		transfer.setParamFunc4(funcion.paramNum());
		Check real = new No();
		if(funcion.getName() == "función 4" && ((Function4) funcion).isReal())
				real = new Si();
		transfer.setCromosomaReal(real);
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
		this.da.addObserver(o);
		this.ba.addObserver(o);
	}
}
