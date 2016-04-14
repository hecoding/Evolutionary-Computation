package model.geneticAlgorithm.crossover;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;

public class PMXCrossover implements CrossoverInterface {

	@Override
	public <T extends AbstractChromosome<?>> void crossover(ArrayList<T> population, Random random, double crossProb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "PMX";
	}

}
