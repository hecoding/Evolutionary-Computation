package controller;

import java.util.ArrayList;

import model.geneticAlgorithm.BooleanGeneticAlgorithm;
import model.geneticAlgorithm.TransferGeneticAlgorithm;
import view.gui.swing.SettingsPanel.Check;
import view.gui.swing.SettingsPanel.Si;
import view.gui.swing.SettingsPanel.No;

public class Controller {
	private BooleanGeneticAlgorithm ga;
	
	public Controller(BooleanGeneticAlgorithm geneticAlgorithm) {
		this.ga = geneticAlgorithm;
	}
	
	public void setParameters(TransferGeneticAlgorithm transfer) {
		double elitePercentage = 0;
		boolean elitism = transfer.getElitismo().getOpcion();
		//if (elitism)
		//	elitePercentage = transfer.getPorcElite();
		
		this.ga = new BooleanGeneticAlgorithm(transfer.getPoblacion(), elitism, elitePercentage, transfer.getGeneraciones(),
				transfer.getPorcCruces(), transfer.getPorcMutacion(), transfer.getPrecision(),
				transfer.getSemillaPersonalizada(), transfer.getSemilla());
	}
	
	public TransferGeneticAlgorithm getParameters() {
		TransferGeneticAlgorithm transfer = new TransferGeneticAlgorithm();
		
		//transfer.setFuncion(this.ga.damelafunc());
		transfer.setPrecision(this.ga.getTolerance());
		transfer.setPoblacion(this.ga.getPopulationNum());
		transfer.setGeneraciones(this.ga.getMaxGenerationNum());
		transfer.setPorcCruces(this.ga.getCrossProb());
		transfer.setPorcMutacion(this.ga.getMutationProb());
		transfer.setSemillaPersonalizada(this.ga.isCustomSeed());
		transfer.setSemilla((int) this.ga.getSeed());
		Check elitism;
		if (this.ga.isUseElitism())
			elitism = new Si();
		else
			elitism = new No();
		transfer.setElitismo(elitism);
		
		return transfer;
	}
	
	public void run() {
		this.ga.run();
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
}
