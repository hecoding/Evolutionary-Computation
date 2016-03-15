package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import model.chromosome.AptitudeComparator;
import model.chromosome.BooleanChromosome;
import model.function.Function;
import model.gene.BooleanGene;

public class BooleanGeneticAlgorithm extends AbstractGeneticAlgorithm {
	private ArrayList<BooleanChromosome> population;
	private ArrayList<BooleanChromosome> elite;
	private BooleanChromosome bestChromosome;
	private double bestAptitude;
	private double averageAptitude;
	private double averageScore;
	private static Comparator<BooleanChromosome> aptitudeComparator;
	private ArrayList<Double> bestChromosomeList;
	private ArrayList<Double> averageAptitudeList;
	private ArrayList<Double> bestAptitudeList;

	public BooleanGeneticAlgorithm(Function function, int populationNum,
			boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		this.function = function;
		this.populationNum = populationNum;
		this.useElitism = useElitism;
		this.elitePercentage = elitePercentage;
		this.population = new ArrayList<BooleanChromosome>(populationNum);
		this.elite = null;
		this.currentGeneration = 0;
		this.maxGenerationNum = maxGenerationNum;
		this.bestChromosome = null;
		this.bestAptitude = 0;
		this.averageAptitude = 0;
		this.averageScore = 0;
		this.crossProb = crossProb;
		this.mutationProb = mutationProb;
		this.tolerance = tolerance;
		this.customSeed = customSeed;
		this.seed = seed;
		this.bestChromosomeList = new ArrayList<Double>(this.maxGenerationNum);
		this.averageAptitudeList = new ArrayList<Double>(this.maxGenerationNum);
		this.bestAptitudeList = new ArrayList<Double>(this.maxGenerationNum);
		
		if(random == null) {
			if(customSeed)
				random = new Random(this.seed);
			else
				random = new Random();
		}
		if(aptitudeComparator == null)
			aptitudeComparator = new AptitudeComparator();
	}
	
	public void restart(Function function, int populationNum, boolean useElitism,
			double elitePercentage, int maxGenerationNum, double crossProb, double mutationProb,
			double tolerance, boolean customSeed, long seed) {
		this.function = function;
		this.populationNum = populationNum;
		this.useElitism = useElitism;
		this.elitePercentage = elitePercentage;
		this.population = new ArrayList<BooleanChromosome>(populationNum);
		this.elite = null;
		this.currentGeneration = 0;
		this.maxGenerationNum = maxGenerationNum;
		this.bestChromosome = null;
		this.bestAptitude = 0;
		this.averageAptitude = 0;
		this.averageScore = 0;
		this.crossProb = crossProb;
		this.mutationProb = mutationProb;
		this.tolerance = tolerance;
		this.customSeed = customSeed;
		this.seed = seed;
		this.bestChromosomeList = new ArrayList<Double>(this.maxGenerationNum);
		this.averageAptitudeList = new ArrayList<Double>(this.maxGenerationNum);
		this.bestAptitudeList = new ArrayList<Double>(this.maxGenerationNum);
		
		this.initialize();
	}
	
	public void initialize() {
		this.population.clear();
		
		for (int i = 0; i < this.populationNum; i++) {
			BooleanChromosome chr = new BooleanChromosome(this.function, this.tolerance, random);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.add(chr);
		}
		
		if(this.function.isMinimization())
			this.aptitudeShifting();
	}
	
	public void run() {
		this.notifyStartRun();
		//GeneticAlgorithm ga = new GeneticAlgorithm(30, 0.10, 10, 0.4, 0.01, 0.0001, 0);
		//GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.10, 100, 0.4, 0.01, 0.0001, 0);
		this.initialize();
		//System.out.println(this);
		this.evaluatePopulation();
		//System.out.println("best: " + System.lineSeparator() + this.getBestChromosome() + " "
		//					+ this.getBestChromosome().getAptitude() + " "
		//					+ this.getBestChromosome().getPhenotype() + System.lineSeparator());
		while(!this.finished()) {
			if (this.useElitism)
				this.elite = this.selectElite();
			this.increaseGeneration();
			this.selection();
			this.reproduction();
			this.mutation();
			if (this.useElitism)
				this.includeElite(this.elite);
			this.evaluatePopulation();
			
			this.bestChromosomeList.add(this.getBestChromosome().getAptitude());
			this.bestAptitudeList.add(this.getBestAptitude());
			this.averageAptitudeList.add(this.getAverageAptitude());
		}
		//System.out.println(this);
		//System.out.println("best at the end: " + System.lineSeparator() + this.getBestChromosome() + " "
		//		+ this.getBestChromosome().getAptitude() + " "
		//		+ this.getBestChromosome().getPhenotype() + System.lineSeparator());
		this.notifyEndRun();
	}
	
	public void evaluatePopulation() {
		double aggregateScore = 0;
		double bestAptitude = 0;
		double aggregateAptitude = 0;
		BooleanChromosome currentBest = null;
		
		// compute best and aggregate aptitude
		for (BooleanChromosome chromosome : this.population) {
			double chromAptitude = chromosome.getAptitude();
			aggregateAptitude += chromAptitude;
			
			if (chromAptitude > bestAptitude) {
				currentBest = chromosome;
				bestAptitude = chromAptitude;
			}
		}
		
		// compute and set score of population individuals
		for (BooleanChromosome chromosome : this.population) {
			chromosome.setScore(chromosome.getAptitude() / aggregateAptitude);
			chromosome.setAggregateScore(chromosome.getScore() + aggregateScore);
			aggregateScore += chromosome.getScore();
		}
		
		// refresh best individual and aptitude statistics
		if (this.bestChromosome == null || bestAptitude > this.bestChromosome.getAptitude()) {
			this.bestChromosome = currentBest;
			this.bestAptitude = bestAptitude;
		}
		this.averageAptitude = aggregateAptitude / this.population.size();
		this.averageScore = aggregateScore / this.population.size();
	}
	
	/* transform minimization problem into maximization */
	private void aptitudeShifting() {
		Double cmax = Double.NEGATIVE_INFINITY;
		
		for (BooleanChromosome chrom : this.population) {
			if(chrom.getAptitude() > cmax)
				cmax = chrom.getAptitude(); // get worst aptitude
		}
		cmax = cmax * 1.05; // evitate aggregateAptitude = 0 when population converges
		
		for (BooleanChromosome chrom : this.population) {
			chrom.setAptitude(cmax - chrom.getAptitude());
		}
	}
	
	/* GENETIC OPERATORS */
	
	public void selection() {
		// roulette selection
		ArrayList<BooleanChromosome> selectedPopulation = new ArrayList<BooleanChromosome>(this.population.size());
		double prob = 0;
		int positionSelected = 0;
		
		for (int i = 0; i < this.population.size(); i++) {
			prob = random.nextDouble();
			positionSelected = 0;
			
			while((prob > this.population.get(positionSelected).getAggregateScore())
					&& (positionSelected < this.population.size()))
				positionSelected++;
			
			selectedPopulation.add(this.population.get(positionSelected));
		}
		
		this.population = selectedPopulation;
	}
	
	public void reproduction() {
		ArrayList<Integer> selectedCandidatesIdx = new ArrayList<Integer>(this.population.size());
		BooleanChromosome child1 = null, child2 = null;
		
		// select randomly all the candidates for the crossover
		for (int i = 0; i < this.population.size(); i++) {
			if(random.nextDouble() < this.crossProb)
				selectedCandidatesIdx.add(i);
		}
		
		// make size even
		if((selectedCandidatesIdx.size() % 2) == 1)
			selectedCandidatesIdx.remove(selectedCandidatesIdx.size() - 1);
		
		// iterate over pairs
		for (int i = 0; i < selectedCandidatesIdx.size(); i+=2) {
			BooleanChromosome parent1 = this.population.get(selectedCandidatesIdx.get(i));
			BooleanChromosome parent2 = this.population.get(selectedCandidatesIdx.get(i + 1));
			child1 = parent1.clone();
			child2 = parent1.clone();
			crossover(parent1, parent2, child1, child2);
			
			this.population.set(selectedCandidatesIdx.get(i), child1);
			this.population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}
	
	private void crossover(BooleanChromosome parent1, BooleanChromosome parent2, BooleanChromosome child1, BooleanChromosome child2) {
		int chromLength = this.population.get(0).getLength();
		// select point over 0 and chromosomeLength - 1
		int crossoverPoint = random.nextInt(chromLength);
		
		ArrayList<BooleanGene> parent1Genes = parent1.getGenotype();
		ArrayList<BooleanGene> parent2Genes = parent2.getGenotype();
		ArrayList<BooleanGene> child1Genes = new ArrayList<BooleanGene>(chromLength);
		ArrayList<BooleanGene> child2Genes = new ArrayList<BooleanGene>(chromLength);
		
		// before the crossover point
		for (int i = 0; i < crossoverPoint; i++) {
			child1Genes.add(parent1Genes.get(i));
			child2Genes.add(parent2Genes.get(i));
		}
		
		// after the crossover point
		for (int i = crossoverPoint; i < chromLength; i++) {
			child1Genes.add(parent2Genes.get(i));
			child2Genes.add(parent1Genes.get(i));
		}
		
		child1.setGenotype(child1Genes);
		child2.setGenotype(child2Genes);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}
	
	public void mutation() {
		ArrayList<BooleanGene> genes;
		boolean mutated = false;
		
		for (BooleanChromosome chrom : this.population) {
			mutated = false;
			genes = chrom.getGenotype();
			
			for (BooleanGene gene : genes) { // each gene can be coded on several bits
				mutated = gene.mutate(this.mutationProb, random);
			}
			
			if(mutated)
				chrom.setAptitude(chrom.evaluate());
		}
	}
	
	public ArrayList<BooleanChromosome> selectElite() {
		int eliteNum = (int) Math.ceil(this.populationNum * this.elitePercentage);
		ArrayList<BooleanChromosome> elite = new ArrayList<BooleanChromosome>(eliteNum);
		
		this.population.sort(aptitudeComparator);
		for (int i = this.populationNum - eliteNum; i < this.populationNum; i++) {
			elite.add(this.population.get(i));
		}
		
		return elite;
	}
	
	public void includeElite(ArrayList<BooleanChromosome> elite) {
		// reemplazo de los individuos peor adaptados
		this.population.sort(aptitudeComparator);
		
		for (int i = 0; i < elite.size(); i++) {
			this.population.set(i, elite.get(i));
		}
	}
	
	public BooleanChromosome getBestChromosome() {
		return bestChromosome;
	}
	
	public double getBestAptitude() {
		return this.bestAptitude;
	}
	
	public double getAverageAptitude() {
		return this.averageAptitude;
	}
	
	public double getAverageScore() {
		return this.averageScore;
	}

	public ArrayList<Double> getBestChromosomeList() {
		return bestChromosomeList;
	}

	public ArrayList<Double> getAverageAptitudeList() {
		return averageAptitudeList;
	}

	public ArrayList<Double> getBestAptitudeList() {
		return bestAptitudeList;
	}

	public String toString() {
		String s = new String();
		
		s += "pos     genotype     aptitude phenotype" + System.lineSeparator();
		for (int i = 0; i < this.population.size(); i++) {
			BooleanChromosome elem = this.population.get(i);
			s += i + " " + elem + " " + String.format("%5f", elem.getAptitude()) + " " + String.format("%.5f", elem.getPhenotype()) + System.lineSeparator();
		}
		
		return s;
	}

}
