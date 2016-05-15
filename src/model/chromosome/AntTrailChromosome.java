package model.chromosome;

import model.Ant;
import model.Map;
import model.genProgAlgorithm.AntTrailGeneticAlgorithm;
import model.genProgAlgorithm.fitnessFunction.FitnessFunctionInterface;
import model.program.Node;
import util.Tree;

public class AntTrailChromosome extends AbstractChromosome {
	private Tree<Node> program;
	
	public AntTrailChromosome() {
		this.program = new Tree<>(null);
	}
	
	public AntTrailChromosome(FitnessFunctionInterface function) {
		fitnessFunc = function;
		this.program = new Tree<>(null);
	}

	@Override
	public void initialize() {
		/*if (!this.genes.isEmpty())
			this.genes.clear();
		
		RandGenerator random = RandGenerator.getInstance();
		int i = 0;
		while (i < TSPGeneticAlgorithm.Cities.number) {
			IntegerGene newGene = new IntegerGene( random.nextInt(TSPGeneticAlgorithm.Cities.number) );
			if(!this.genes.contains(newGene)) { // Overrode .equals()
				this.genes.add( newGene );
				i++;
			}
		}*/
	}
	
	@Override
	public double evaluate() {
		Map map = AntTrailGeneticAlgorithm.getMap();
		Ant ant = new Ant();
		// TODO Auto-generated method stub
		return fitnessFunc.f(null/*cosas*/);
	}

	@Override
	public String getPhenotype() {
		return this.program.preOrder();
	}

	@Override
	public AntTrailChromosome clone() {
		/*// deep copy indeed
		AntTrailChromosome chr = new AntTrailChromosome();
		ArrayList<IntegerGene> clonedList = new ArrayList<IntegerGene>(genes.size());
		for (IntegerGene gene : genes) {
			clonedList.add(gene.clone());
		}
		
		chr.setGenotype(clonedList);
		chr.aggregateScore = this.aggregateScore;
		chr.aptitude = this.aptitude;
		chr.score = this.score;
		
		return chr;*/
		return null;
	}

	@Override
	public String toString() {
		return this.program.toString();
	}

}
