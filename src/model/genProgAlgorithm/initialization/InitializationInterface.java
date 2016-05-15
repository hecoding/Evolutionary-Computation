package model.genProgAlgorithm.initialization;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;

public interface InitializationInterface {
	public <T extends AbstractChromosome> void initialize(ArrayList<T> population, int programDepth);
	public String getName();
}
