package model.function;

import java.util.ArrayList;

public abstract class Function {
	protected boolean minimization;
	protected int varNum;
	protected ArrayList<Limit> limits;
	protected String name;
	
	public abstract double f(ArrayList<Double> params);

	public String getName() {
		return name;
	}

	public boolean isMinimization() {
		return minimization;
	}
	
	public int paramNum() {
		return this.varNum;
	}
	
	public ArrayList<Limit> getLimits() {
		return limits;
	}

	public class Limit {
		public double minx;
		public double maxx;
		
		public Limit() {
			this.minx = 0;
			this.maxx = 0;
		}
		
		public Limit(double minx, double maxx) {
			this.minx = minx;
			this.maxx = maxx;
		}
	}
}
