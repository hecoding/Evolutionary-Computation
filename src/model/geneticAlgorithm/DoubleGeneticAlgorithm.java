package model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import model.chromosome.AbstractChromosome;
import model.chromosome.AptitudeComparator;
import model.chromosome.DoubleChromosome;
import model.function.Function;
import model.gene.DoubleGene;

public class DoubleGeneticAlgorithm extends AbstractGeneticAlgorithm {
	
	private ArrayList<DoubleChromosome> population;
	private ArrayList<DoubleChromosome> elite;
	private DoubleChromosome bestChromosome;
	private ArrayList<Double> inspectedAptitude;
	private double bestAptitude;
	private double averageAptitude;
	private double averageScore;
	private ArrayList<Double> bestChromosomeList;
	private ArrayList<Double> averageAptitudeList;
	private ArrayList<Double> bestAptitudeList;
	private static Comparator<AbstractChromosome> aptitudeComparator;

	public DoubleGeneticAlgorithm(Function func, int populationNum,
			boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb, double tolerance, boolean customSeed, long seed) {
		function = func;
		this.populationNum = populationNum;
		this.useElitism = useElitism;
		this.elitePercentage = elitePercentage;
		this.population = new ArrayList<DoubleChromosome>(populationNum);
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

	private void includeElite(ArrayList<DoubleChromosome> elite2) {
		// reemplazo de los individuos peor adaptados
		this.population.sort(aptitudeComparator);
		
		for (int i = 0; i < elite.size(); i++) {
			this.population.set(i, elite.get(i));
		}
	}

	private ArrayList<DoubleChromosome> selectElite() {
		int eliteNum = (int) Math.ceil(this.populationNum * this.elitePercentage);
		ArrayList<DoubleChromosome> elite = new ArrayList<DoubleChromosome>(eliteNum);
		
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

	private void evaluatePopulation() {
		double aggregateScore = 0;
		double bestAptitude = Double.NEGATIVE_INFINITY;
		if(this.population.get(0).getFunction().isMinimization()) {
			bestAptitude = Double.POSITIVE_INFINITY;
		}
		double aggregateAptitude = 0;
		double aggregateInspectedAptitude = 0;
		DoubleChromosome currentBest = null;
		
		// compute best and aggregate aptitude
		int i = 0;
		for (DoubleChromosome chromosome : this.population) {
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
		for (DoubleChromosome chromosome : this.population) {
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

	private void aptitudeShifting() {
		this.inspectedAptitude.clear();
		Double cmax = Double.NEGATIVE_INFINITY;
		
		for (DoubleChromosome chrom : this.population) {
			if(chrom.getAptitude() > cmax)
				cmax = chrom.getAptitude(); // get worst aptitude
		}
		cmax = cmax * 1.05; // avoid aggregateAptitude = 0 when population converges
		
		for (DoubleChromosome chrom : this.population) {
			this.inspectedAptitude.add(cmax - chrom.getAptitude());
		}
	}

	@Override
	public void selection() {
		// roulette selection
		ArrayList<DoubleChromosome> selectedPopulation = new ArrayList<DoubleChromosome>(this.population.size());
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

	@Override
	public void reproduction() {
		ArrayList<Integer> selectedCandidatesIdx = new ArrayList<Integer>(this.population.size());
		DoubleChromosome child1 = null, child2 = null;
		
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
			DoubleChromosome parent1 = this.population.get(selectedCandidatesIdx.get(i));
			DoubleChromosome parent2 = this.population.get(selectedCandidatesIdx.get(i + 1));
			child1 = parent1.clone(); child1.setGenotype(new ArrayList<DoubleGene>());
			child2 = parent1.clone(); child2.setGenotype(new ArrayList<DoubleGene>());
			crossover(parent1, parent2, child1, child2);
			
			this.population.set(selectedCandidatesIdx.get(i), child1);
			this.population.set(selectedCandidatesIdx.get(i + 1), child2);
		}
	}

	private void crossover(DoubleChromosome parent1, DoubleChromosome parent2,
			DoubleChromosome child1, DoubleChromosome child2) {
		
		int currentGeneLength = this.population.get(0).getLength();
			
		// select point over 0 and the current gene length - 1
		int crossoverPoint = random.nextInt(currentGeneLength);
		
		ArrayList<DoubleGene> parent1Gene = parent1.getGenotype();
		ArrayList<DoubleGene> parent2Gene = parent2.getGenotype();
		ArrayList<DoubleGene> child1Gene = new ArrayList<DoubleGene>(currentGeneLength);
		ArrayList<DoubleGene> child2Gene = new ArrayList<DoubleGene>(currentGeneLength);
		
		// before the crossover point
		for (int j = 0; j < crossoverPoint; j++) {
			child1Gene.add(parent1Gene.get(j));
			child2Gene.add(parent2Gene.get(j));
		}
		
		// after the crossover point
		for (int j = crossoverPoint; j < currentGeneLength; j++) {
			child1Gene.add(parent2Gene.get(j));
			child2Gene.add(parent1Gene.get(j));
		}
		
		child1.setGenotype(child1Gene);
		child2.setGenotype(child2Gene);
		
		child1.setAptitude(child1.evaluate());
		child2.setAptitude(child2.evaluate());
	}

	@Override
	public void mutation() {
		ArrayList<DoubleGene> genes;
		boolean mutated = false;
		
		for (DoubleChromosome chrom : this.population) {
			mutated = false;
			genes = chrom.getGenotype();
			
			for (DoubleGene gene : genes) {
				mutated = gene.mutate(this.mutationProb, random, chrom.getMaxRange());
			}
			
			if(mutated)
				chrom.setAptitude(chrom.evaluate());
		}
	}

	@Override
	public void restart(Function func, int populationNum, boolean useElitism,
			double elitePercentage, int maxGenerationNum, double crossProb,
			double mutationProb, double tolerance, boolean customSeed, long seed) {
		function = func;
		this.populationNum = populationNum;
		this.useElitism = useElitism;
		this.elitePercentage = elitePercentage;
		this.population = new ArrayList<DoubleChromosome>(populationNum);
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

	private void initialize() {
		this.population.clear();
		
		for (int i = 0; i < this.populationNum; i++) {
			DoubleChromosome chr = new DoubleChromosome(function, this.tolerance, random);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.add(chr);
		}
	}
	
	public DoubleChromosome getBestChromosome() {
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
			DoubleChromosome elem = this.population.get(i);
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
