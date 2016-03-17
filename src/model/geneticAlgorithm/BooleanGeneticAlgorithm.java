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
	private ArrayList<Double> inspectedAptitude;
	private double bestAptitude;
	private double averageAptitude;
	private double averageScore;
	private static Comparator<BooleanChromosome> aptitudeComparator;
	private ArrayList<Double> bestChromosomeList;
	private ArrayList<Double> averageAptitudeList;
	private ArrayList<Double> bestAptitudeList;

	public BooleanGeneticAlgorithm(Function func, int populationNum,
			boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		function = func;
		this.populationNum = populationNum;
		this.useElitism = useElitism;
		this.elitePercentage = elitePercentage;
		this.population = new ArrayList<BooleanChromosome>(populationNum);
		this.elite = null;
		this.currentGeneration = 0;
		this.maxGenerationNum = maxGenerationNum;
		this.bestChromosome = null;
		this.inspectedAptitude = new ArrayList<Double>(populationNum);
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
	
	public void restart(Function func, int populationNum, boolean useElitism,
			double elitePercentage, int maxGenerationNum, double crossProb, double mutationProb,
			double tolerance, boolean customSeed, long seed) {
		function = func;
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
			BooleanChromosome chr = new BooleanChromosome(function, this.tolerance, random);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.add(chr);
		}
	}
	
	public void run() {
		this.notifyStartRun();
		
		this.initialize();
		if(function.isMinimization())
			this.aptitudeShifting();
		this.evaluatePopulation();
		
		while(!this.finished()) {
			if (this.useElitism)
				this.elite = this.selectElite();
			this.increaseGeneration();
			this.selection();
			this.reproduction();
			this.mutation();
			if (this.useElitism)
				this.includeElite(this.elite);
			if(function.isMinimization())
				this.aptitudeShifting();
			this.evaluatePopulation();
			
			this.bestChromosomeList.add(this.getBestChromosome().getAptitude());
			this.bestAptitudeList.add(this.getBestAptitude());
			this.averageAptitudeList.add(this.getAverageAptitude());
		}
		
		this.notifyEndRun();
	}
	
	public void evaluatePopulation() {
		double aggregateScore = 0;
		double bestAptitude = Double.NEGATIVE_INFINITY;
		if(this.population.get(0).getFunction().isMinimization()) {
			bestAptitude = Double.POSITIVE_INFINITY;
		}
		double aggregateAptitude = 0;
		double aggregateInspectedAptitude = 0;
		BooleanChromosome currentBest = null;
		
		// compute best and aggregate aptitude
		int i = 0;
		for (BooleanChromosome chromosome : this.population) {
			double chromAptitude = chromosome.getAptitude();
			aggregateAptitude += chromAptitude;
			aggregateInspectedAptitude += this.inspectedAptitude.get(i);
			
			if(this.population.get(0).getFunction().isMinimization()) {
				if (chromAptitude < bestAptitude) {
					currentBest = chromosome;
					bestAptitude = chromAptitude;
				}
			}
			else {
				if (chromAptitude > bestAptitude) {
					currentBest = chromosome;
					bestAptitude = chromAptitude;
				}
			}
			i++;
		}
		
		// compute and set score of population individuals
		i = 0;
		for (BooleanChromosome chromosome : this.population) {
			chromosome.setScore(this.inspectedAptitude.get(i) / aggregateInspectedAptitude);
			chromosome.setAggregateScore(chromosome.getScore() + aggregateScore);
			aggregateScore += chromosome.getScore();
			i++;
		}
		
		// refresh best individual and aptitude statistics
		if(this.population.get(0).getFunction().isMinimization()) {
			if (this.bestChromosome == null || bestAptitude < this.bestChromosome.getAptitude()) {
				this.bestChromosome = currentBest.clone();
			}
		}
		else {			
			if (this.bestChromosome == null || bestAptitude > this.bestChromosome.getAptitude()) {
				this.bestChromosome = currentBest.clone();
			}
		}
		this.bestAptitude = bestAptitude;
		this.averageAptitude = aggregateAptitude / this.population.size();
		this.averageScore = aggregateScore / this.population.size();
	}
	
	/* transform minimization problem into maximization */
	private void aptitudeShifting() {
		this.inspectedAptitude.clear();
		Double cmax = Double.NEGATIVE_INFINITY;
		
		for (BooleanChromosome chrom : this.population) {
			if(chrom.getAptitude() > cmax)
				cmax = chrom.getAptitude(); // get worst aptitude
		}
		cmax = cmax * 1.05; // avoid aggregateAptitude = 0 when population converges
		
		for (BooleanChromosome chrom : this.population) {
			this.inspectedAptitude.add(cmax - chrom.getAptitude());
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
			
			while((positionSelected < this.population.size()) &&
					(prob > this.population.get(positionSelected).getAggregateScore()))
				positionSelected++;
			
			selectedPopulation.add(this.population.get(positionSelected).clone());
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
			child1 = parent1.clone(); child1.setGenotype(new ArrayList<BooleanGene>());
			child2 = parent1.clone(); child2.setGenotype(new ArrayList<BooleanGene>());
			crossover(parent1, parent2, child1, child2);
			
			this.population.set(selectedCandidatesIdx.get(i), child1);
			this.population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}
	
	private void crossover(BooleanChromosome parent1, BooleanChromosome parent2, BooleanChromosome child1, BooleanChromosome child2) {
		int geneNum = this.population.get(0).getGeneNum();
		for (int i = 0; i < geneNum; i++) {
			int currentGeneLength = this.population.get(0).getGenotype().get(i).getLength();
			
			// select point over 0 and the current gene length - 1
			int crossoverPoint = random.nextInt(currentGeneLength);
			
			BooleanGene parent1Gene = parent1.getGenotype().get(i);
			BooleanGene parent2Gene = parent2.getGenotype().get(i);
			BooleanGene child1Gene = new BooleanGene(currentGeneLength);
			BooleanGene child2Gene = new BooleanGene(currentGeneLength);
			
			// before the crossover point
			for (int j = 0; j < crossoverPoint; j++) {
				child1Gene.add(parent1Gene.getInformation().get(j));
				child2Gene.add(parent2Gene.getInformation().get(j));
			}
			
			// after the crossover point
			for (int j = crossoverPoint; j < currentGeneLength; j++) {
				child1Gene.add(parent2Gene.getInformation().get(j));
				child2Gene.add(parent1Gene.getInformation().get(j));
			}
			
			child1.add(child1Gene);
			child2.add(child2Gene);
		}
		
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
		if(this.population.get(0).getFunction().isMinimization()) {
			for (int i = 0; i < eliteNum; i++) {
				elite.add(this.population.get(i).clone());
			}
		}
		else {			
			for (int i = this.populationNum - eliteNum; i < this.populationNum; i++) {
				elite.add(this.population.get(i).clone());
			}
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
		if(this.population == null || this.population.size() == 0) return "";
		String s = new String();
		
		s += "pos     genotype     aptitude phenotype" + System.lineSeparator();
		for (int i = 0; i < this.population.size(); i++) {
			BooleanChromosome elem = this.population.get(i);
			s += i + " " + elem + " " + String.format("%5f", elem.getAptitude()) + " ";
			
			for (Double el : elem.getPhenotype()) {
				s += String.format("%.5f", el) + " ";
			}
			s = s.substring(0, s.length() - 1);
			
			s += System.lineSeparator();
		}
		
		return s;
	}

}
