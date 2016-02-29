package model.Chromosome;

public class Chromosome extends AbstractChromosome<Gene> {
	private int minx;
	private int maxx;
	private int length;
	
	public Chromosome(int minx, int maxx, int length) {
		this.minx = minx;
		this.maxx = maxx;
		this.length = length;
	}

	@Override
	public double evalua() {
		return this.f((double) this.phenotype());
	}

	@Override
	public Object phenotype() {
		// TODO Auto-generated method stub
		return null;
	}
	
	double f(double x) {
		return 0;
	}

}
