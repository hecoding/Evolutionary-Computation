package model.function;

import java.util.ArrayList;

public class Function1 extends Function {
	
	public Function1() {
		this.name = "función 1";
		this.minimization = true;
		this.varNum = 1;
		this.limits = new ArrayList<Limit>(this.varNum);
		
		Limit xLimit = new Limit(-250, 250);
		this.limits.add(xLimit);
	}

	@Override
	public double f(ArrayList<Double> params) {
		double x = params.get(0);
		
		return -Math.abs(x * Math.sin( Math.sqrt(Math.abs(x)) ));
	}

}
