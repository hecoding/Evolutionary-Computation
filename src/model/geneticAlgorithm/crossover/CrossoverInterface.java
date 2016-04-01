package model.geneticAlgorithm.crossover;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;

public interface CrossoverInterface {
	public <T extends AbstractChromosome<?>> void crossover(ArrayList<T> population, Random random, double crossProb);
	public String getName();
}
