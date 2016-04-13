package model.geneticAlgorithm.mutation;

import java.util.ArrayList;
import java.util.Random;

import model.chromosome.AbstractChromosome;
import model.chromosome.exception.IllegalChromosomeException;
import model.gene.BooleanGene;
import util.RandGenerator;

public class BitFlipMutation implements MutationInterface {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractChromosome<?>> void mutate(ArrayList<T> population, double mutationProb) {
		if(!(population.get(0).getGenotype().get(0) instanceof BooleanGene)) {
			throw new IllegalChromosomeException(population.get(0).getGenotype().get(0).getClass(), BooleanGene.class);
		}
		
		Random random = RandGenerator.getInstance();
		ArrayList<BooleanGene> genes;
		boolean mutated = false;
		
		for (T chrom : population) {
			mutated = false;
			genes = (ArrayList<BooleanGene>) chrom.getGenotype();
			
			for (BooleanGene gene : genes) { // each gene can be coded on several bits
				mutated = mutate(gene, mutationProb, random);
			}
			
			if(mutated)
				chrom.setAptitude(chrom.evaluate());
		}
	}
	
	public boolean mutate(BooleanGene gene, double mutationProb, Random random) {
		boolean mutated = false;
		
		for (int i = 0; i < gene.getInformation().size(); i++) {
			if(random.nextDouble() < mutationProb) {
				ArrayList<Boolean> inf = gene.getInformation();
				
				inf.set(i, !inf.get(i));
				
				//gene.setInformation(inf); not needed because we use its reference
				mutated = true;
			}
		}
		
		return mutated;
	}

	@Override
	public String getName() {
		return "bit a bit";
	}

}
