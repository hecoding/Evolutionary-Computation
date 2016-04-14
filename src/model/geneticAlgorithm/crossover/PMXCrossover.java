package model.geneticAlgorithm.crossover;

import java.util.ArrayList;
import model.chromosome.AbstractChromosome;

public class PMXCrossover implements CrossoverInterface {

	@Override
	public <T extends AbstractChromosome<?>> void crossover(ArrayList<T> population, double crossProb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "PMX";
	}

}
