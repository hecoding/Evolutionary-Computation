package main;

import java.util.ArrayList;

import model.Chromosome.BooleanChromosome;
import model.Chromosome.BooleanGene;

public class Main {

	public static void main(String[] args) {
		//MainWindow view = new MainWindow();
		
		ArrayList<Boolean> array = new ArrayList<Boolean>();
		array.add(false);array.add(true);
		BooleanGene g = new BooleanGene(array);
		System.out.println(g);
		
		BooleanChromosome chr = new BooleanChromosome(-250, 250, 0.001, 0);
		chr.initialize();
		System.out.println(chr);
	}

}
