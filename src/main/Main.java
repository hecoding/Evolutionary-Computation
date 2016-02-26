package main;

import java.util.ArrayList;

import model.Chromosome.Gene;

public class Main {

	public static void main(String[] args) {
		//MainWindow view = new MainWindow();
		
		Gene g = new Gene();
		ArrayList<Boolean> array = new ArrayList<Boolean>();
		array.add(false);array.add(true);
		g.setGenotype(array);
		
		System.out.println(g);
	}

}
