package model.geneticAlgorithm.selection;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;

public interface SelectionInterface {
	public <T extends AbstractChromosome<?>> void select(ArrayList<T> population, Random random);
	public String getName();
}
