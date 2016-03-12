package main;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

import model.GeneticAlgorithm;
import model.Chromosome.BooleanChromosome;

public class Main {

	public static void main(String[] args) {
		//MainWindow view = new MainWindow();
		
		//ArrayList<Boolean> array = new ArrayList<Boolean>();
		//array.add(false);array.add(true);
		//BooleanGene g = new BooleanGene(array);
		//System.out.println(g);
		
		//BooleanChromosome chr = new BooleanChromosome(-250, 250, 0.001, new Random(0));
		//chr.initialize();
		//System.out.println(chr);
		
		JFrame frame = new JFrame();
		
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
		
		frame.setContentPane(plot);
	}
	
	public static void runGA(ArrayList<Double> bestChroms, ArrayList<Double> averageApt, ArrayList<Double> maxApt) {
		boolean usingElitism = false;
		ArrayList<BooleanChromosome> elite = null;
		//GeneticAlgorithm ga = new GeneticAlgorithm(30, 0.10, 10, 0.4, 0.01, 0.0001, 0);
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.10, 100, 0.4, 0.01, 0.0001, 0);
		ga.initialize();
		System.out.println(ga);
		ga.evaluatePopulation();
		System.out.println("best: " + System.lineSeparator() + ga.getBestChromosome() + " "
							+ ga.getBestChromosome().getAptitude() + " "
							+ ga.getBestChromosome().getPhenotype() + System.lineSeparator());
		while(!ga.finished()) {
			if (usingElitism)
				elite = ga.selectElite();
			ga.increaseGeneration();
			ga.selection();
			ga.reproduction();
			ga.mutation();
			if (usingElitism)
				ga.includeElite(elite);
			ga.evaluatePopulation();
			bestChroms.add(ga.getBestChromosome().getAptitude());
			maxApt.add(ga.getBestAptitude());
			averageApt.add(ga.getAverageAptitude());
		}
		System.out.println(ga);
		System.out.println("best at the end: " + System.lineSeparator() + ga.getBestChromosome() + " "
				+ ga.getBestChromosome().getAptitude() + " "
				+ ga.getBestChromosome().getPhenotype() + System.lineSeparator());
	}
	
	private static double[] toPrimitiveArray(ArrayList<Double> a) {
		return a.stream().mapToDouble(d -> d).toArray();
	}

}
