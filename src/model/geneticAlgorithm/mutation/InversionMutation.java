package model.geneticAlgorithm.mutation;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.gene.AbstractGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import util.RandGenerator;

public class InversionMutation implements MutationInterface {

	@Override
	public <T extends AbstractChromosome<?>> void mutate(ArrayList<T> population, double mutationProb) {
		RandGenerator random = RandGenerator.getInstance();
		
		for (T chrom : population) {
			if(random.nextDouble() < mutationProb) {
				mutate(chrom, mutationProb);
				chrom.setAptitude(chrom.evaluate());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractChromosome<?>> void mutate(T chrom, double mutationProb) {
		RandGenerator random = RandGenerator.getInstance();
		ArrayList<AbstractGene<?>> genes = (ArrayList<AbstractGene<?>>) chrom.getGenotype();
		
		// select two points over 0 and the current gene length - 1
		int point1 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		int point2 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		if (point1 > point2) {
			int temp = point1;
			point1 = point2;
			point2 = temp;
		}
		ArrayList<AbstractGene<?>> inversion = new ArrayList<AbstractGene<?>>(point2 - point1);
		
		for (int i = point1; i <= point2; i++) {
			inversion.add(genes.get(i));
		}
		
		for (int i = point2, j = 0; i > point1 || j < inversion.size(); i--, j++) {
			genes.set(i, inversion.get(j));
		}
		
	}

	@Override
	public String getName() {
		return "Inversión";
	}

}
