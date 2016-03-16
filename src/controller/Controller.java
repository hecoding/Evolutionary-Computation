package controller;

import java.util.ArrayList;

import model.function.Function;
import model.function.FunctionFactory;
import model.geneticAlgorithm.BooleanGeneticAlgorithm;
import model.geneticAlgorithm.TransferGeneticAlgorithm;
import model.observer.GeneticAlgorithmObserver;
import view.gui.swing.SettingsPanel.Check;
import view.gui.swing.SettingsPanel.Si;
import view.gui.swing.SettingsPanel.No;
import view.gui.swing.SettingsPanel.Percentage;

public class Controller {
	private BooleanGeneticAlgorithm ga;
	
	public Controller(BooleanGeneticAlgorithm geneticAlgorithm) {
		this.ga = geneticAlgorithm;
	}
	
	public void setParameters(TransferGeneticAlgorithm transfer) {
		Function function = FunctionFactory.getInstance().create(transfer.getFuncion());
		double elitePercentage = 0;
		boolean elitism = transfer.getElitismo().getOpcion();
		elitePercentage = transfer.getPorcElite().getPerc();
		
		this.ga.restart(function, transfer.getPoblacion(), elitism, elitePercentage,
				transfer.getGeneraciones(), transfer.getPorcCruces(), transfer.getPorcMutacion(),
				transfer.getPrecision(), transfer.getSemillaPersonalizada(), transfer.getSemilla());
	}
	
	public TransferGeneticAlgorithm getParameters() {
		TransferGeneticAlgorithm transfer = new TransferGeneticAlgorithm();
		Check elitism;
		Percentage porcElite;
		
		transfer.setFuncion(this.ga.getFunction().getName());
		transfer.setPrecision(this.ga.getTolerance());
		transfer.setPoblacion(this.ga.getPopulationNum());
		transfer.setGeneraciones(this.ga.getMaxGenerationNum());
		transfer.setPorcCruces(this.ga.getCrossProb());
		transfer.setPorcMutacion(this.ga.getMutationProb());
		transfer.setSemillaPersonalizada(this.ga.isCustomSeed());
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
		return this.ga.getBestChromosome().getFunction().f(this.ga.getBestChromosome().getPhenotype());
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
