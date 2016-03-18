package main;

import controller.Controller;
import view.gui.swing.MainWindow;

public class Main {

	public static void main(String[] args) {
		Controller ctrl = new Controller();
		@SuppressWarnings("unused")
		MainWindow view = new MainWindow(ctrl);
		
		/*ga.run();
		System.out.println(ga);
		System.out.println("best: " + ga.getBestChromosome() + " " + ga.getBestChromosome().getAptitude() + " " + ga.getBestChromosome().getPhenotype());*/
	}
	
}
