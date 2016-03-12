package main;

import controller.Controller;
import model.geneticAlgorithm.BooleanGeneticAlgorithm;
import view.gui.swing.MainWindow;

public class Main {

	public static void main(String[] args) {
		BooleanGeneticAlgorithm ga = new BooleanGeneticAlgorithm(100, false, 0.1, 100, 0.6, 0.05, 0.001, true, 0);
		Controller ctrl = new Controller(ga);
		MainWindow view = new MainWindow(ctrl);
		
		
		/*JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1024,768));
		frame.setMinimumSize(new Dimension(750, 550));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		Plot2DPanel plot = new Plot2DPanel();
		ArrayList<Double> bestChroms = new ArrayList<Double>();
		ArrayList<Double> averageApt = new ArrayList<Double>();
		ArrayList<Double> maxApt = new ArrayList<Double>();
		
		runGA(bestChroms, averageApt, maxApt);
		
		plot.addLegend("SOUTH");
		plot.addLinePlot("Mejor absoluto", toPrimitiveArray(bestChroms));
		plot.addLinePlot("Mejor de la generación", toPrimitiveArray(maxApt));
		plot.addLinePlot("Media de la generación", toPrimitiveArray(averageApt));
		
		frame.setContentPane(plot);*/
		
		/*ga.run();
		System.out.println(ga);
		System.out.println("best: " + ga.getBestChromosome() + " " + ga.getBestChromosome().getAptitude() + " " + ga.getBestChromosome().getPhenotype());*/
	}
	
}
