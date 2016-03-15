package model.function;

import java.util.ArrayList;

public class Function3 extends Function {
	
	public Function3() {
		this.name = "función 3";
		this.minimization = false;
		this.varNum = 2;
		this.limits = new ArrayList<Limit>(this.varNum);
		
		Limit xLimit = new Limit(-3, 12.1);
		Limit yLimit = new Limit(4.1, 5.8);
		this.limits.add(xLimit);
		this.limits.add(yLimit);
	}

	@Override
	public double f(ArrayList<Double> params) {
		double x = params.get(0);
		double y = params.get(1);
		
		return 21.5 + x * Math.sin(4 * Math.PI * x) + y * Math.sin(20 * Math.PI * y);
	}

}
