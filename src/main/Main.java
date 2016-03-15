package main;

import controller.Controller;
import model.function.Function1;
import model.geneticAlgorithm.BooleanGeneticAlgorithm;
import view.gui.swing.MainWindow;

public class Main {

	public static void main(String[] args) {
		BooleanGeneticAlgorithm ga = new BooleanGeneticAlgorithm(new Function1(), 100, false, 0.1, 100, 0.6, 0.05, 0.001, true, 0);
		Controller ctrl = new Controller(ga);
		@SuppressWarnings("unused")
		MainWindow view = new MainWindow(ctrl);
		
		/*ga.run();
		System.out.println(ga);
		System.out.println("best: " + ga.getBestChromosome() + " " + ga.getBestChromosome().getAptitude() + " " + ga.getBestChromosome().getPhenotype());*/
	}
	
}
