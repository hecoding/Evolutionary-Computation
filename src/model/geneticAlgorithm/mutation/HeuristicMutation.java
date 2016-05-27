package model.geneticAlgorithm.mutation;

import java.util.ArrayList;

import model.chromosome.AbstractChromosome;
import model.chromosome.TSPChromosome;
import model.chromosome.comparator.AptitudeComparator;
import model.gene.AbstractGene;
import model.gene.IntegerGene;
import model.geneticAlgorithm.TSPGeneticAlgorithm;
import util.Permutations;
import util.RandGenerator;

public class HeuristicMutation implements MutationInterface {

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
		ArrayList<TSPChromosome> chromPerms;
		
		// select three points over 0 and the current gene length - 1
		int point1, point2, point3 = 0;
		do {
			point1 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
			point2 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
			point3 = random.nextInt(TSPGeneticAlgorithm.Cities.number);
		} while (point1 == point2 || point1 == point3 || point2 == point3);
		
		// get the numbers
		int[] nums = {(int)(genes.get(point1).getInformation()), (int)(genes.get(point2).getInformation()), (int)(genes.get(point3).getInformation())};
		// get the permutations
		ArrayList<ArrayList<Integer>> perms = Permutations.permute(nums);
		chromPerms = new ArrayList<TSPChromosome>(perms.size());
		
		for (ArrayList<Integer> perm : perms) {
			ArrayList<AbstractGene<?>> tempGenes = (ArrayList<AbstractGene<?>>) genes.clone();
			tempGenes.set(point1, new IntegerGene(perm.get(0)));
			tempGenes.set(point2, new IntegerGene(perm.get(1)));
			tempGenes.set(point3, new IntegerGene(perm.get(2)));
			AbstractChromosome<?> tempChrom = chrom.clone();
			tempChrom.setGenotype(tempGenes);
			tempChrom.setAptitude(tempChrom.evaluate());
			
			chromPerms.add((TSPChromosome) tempChrom);
		}
		
		chromPerms.sort(new AptitudeComparator());
		((TSPChromosome) chrom).setGenotype(chromPerms.get(0).getGenotype());
	}

	@Override
	public String getName() {
		return "Heuristic";
	}

}
