package main;

import model.GeneticAlgorithm;

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
		
		GeneticAlgorithm ga = new GeneticAlgorithm(30, 10, 0.4, 0.01, 0.0001, 0);
		ga.initialize();
		System.out.println(ga);
		ga.evaluatePopulation();
		System.out.println("best: " + System.lineSeparator() + ga.getBestChromosome() + " "
							+ ga.getBestChromosome().getAptitude() + " "
							+ ga.getBestChromosome().getPhenotype() + System.lineSeparator());
		while(!ga.finished()) {
			ga.increaseGeneration();
			ga.selection();
			ga.reproduction();
			ga.mutation();
			ga.evaluatePopulation();
		}
		System.out.println(ga);
		System.out.println("best at the end: " + System.lineSeparator() + ga.getBestChromosome() + " "
				+ ga.getBestChromosome().getAptitude() + " "
				+ ga.getBestChromosome().getPhenotype() + System.lineSeparator());
	}

}
