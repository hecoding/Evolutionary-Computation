package model.geneticAlgorithm.mutation;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;

public class InversionMutation implements MutationInterface {

	@Override
	public <T extends AbstractChromosome<?>> void mutate(ArrayList<T> population, double mutationProb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Inversión";
	}

}
