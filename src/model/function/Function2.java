package model.function;

import java.util.ArrayList;

public class Function2 extends Function {
	
	public Function2() {
		this.name = "función 2";
		this.minimization = false;
		this.varNum = 2;
		this.limits = new ArrayList<Limit>(this.varNum);
		
		Limit xLimit = new Limit(-6, 6);
		Limit yLimit = new Limit(-6, 6);
		this.limits.add(xLimit);
		this.limits.add(yLimit);
	}

	@Override
	public double f(ArrayList<Double> params) {
		double x = params.get(0);
		double y = params.get(1);
		
		return (2186 - Math.pow((Math.pow(x, 2) + y + 11), 2) - Math.pow((x + Math.pow(y, 2) - 7), 2)) / 2186;
	}

}
