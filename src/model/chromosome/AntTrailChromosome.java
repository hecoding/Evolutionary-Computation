package model.chromosome;

import java.util.ArrayList;

import model.Ant;
import model.Map;
import model.Map.CellType;
import model.genProgAlgorithm.AntTrailGeneticAlgorithm;
import model.genProgAlgorithm.fitnessFunction.FitnessFunctionInterface;
import model.program.Function;
import model.program.Node;
import model.program.Terminal;
import util.Tree;

public class AntTrailChromosome extends AbstractChromosome {
	private Tree<Node> program;
	private static int maxSteps;
	
	public AntTrailChromosome() {
		this.program = new Tree<>(null);
	}
	
	public AntTrailChromosome(FitnessFunctionInterface function, int maxNumOfSteps) {
		fitnessFunc = function;
		maxSteps = maxNumOfSteps;
		this.program = new Tree<>(null);
	}
	
	@Override
	public double evaluate() {
		Map map = AntTrailGeneticAlgorithm.getMap();
		Ant ant = new Ant();
		ArrayList<Double> steps = new ArrayList<>(3);

		runProgram(this.program, map, ant);
		steps.add((double) ant.getNumberOfBitsEaten());
		steps.add((double) ant.getNumberOfSteps());
		steps.add((double) maxSteps);
		
		return fitnessFunc.f(steps);
	}
	
	public static void runProgram(Tree<Node> program, Map map, Ant ant) {
		// nomnom
		if(map.get(ant.getPosition()) == CellType.food)
			map.set(CellType.eatenfood, ant.getPosition());

		if(ant.steps < maxSteps && ant.bitsEaten < map.getFoodHere()) {
		
			// functions
			if(program.getValue() == Function.sic) {
				if(map.get(ant.getForwardPosition()) == CellType.food)
					runProgram(program.getLeftChild(), map, ant);
				else
					runProgram(program.getRightChild(), map, ant);
			}
			else if(program.getValue() == Function.progn3) {
				for (int i = 0; i < Function.numberOfChildren(Function.progn3); i++) {
					runProgram(program.getNChild(i), map, ant);
				}
			}
			else if(program.getValue() == Function.progn2) {
				runProgram(program.getLeftChild(), map, ant);
				runProgram(program.getRightChild(), map, ant);
			}
			// terminals
			else if(program.getValue() == Terminal.forward)
				ant.moveForward();
			else if(program.getValue() == Terminal.left)
				ant.turnLeft();
			else if(program.getValue() == Terminal.right)
				ant.turnRight();
		}
	}

	@Override
	public String getPhenotype() {
		return this.program.preOrder();
	}
	
	public Tree<Node> getProgram() {
		return this.program;
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
	
	public void setProgram(Tree<Node> program) {
		this.program = program;
	}

	@Override
	public String toString() {
		return this.program.toString();
	}

}
