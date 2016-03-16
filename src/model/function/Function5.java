package model.function;

import java.util.ArrayList;

public class Function5 extends Function {
	
	public Function5() {
		this.name = "función 5";
		this.minimization = true;
		this.varNum = 2;
		this.limits = new ArrayList<Limit>(this.varNum);
		
		Limit xLimit = new Limit(-10, 10);
		Limit yLimit = new Limit(-10, 10);
		this.limits.add(xLimit);
		this.limits.add(yLimit);
	}

	@Override
	public double f(ArrayList<Double> params) {
		double x = params.get(0);
		double y = params.get(1);
		double r1 = 0;
		double r2 = 0;
		
		for (int i = 1; i <= 5; i++) {
			r1 += i * Math.cos((i + 1) * x + i);
		}
		
		for (int i = 1; i <= 5; i++) {
			r2 += i * Math.cos((i + 1) * y + i);
		}
		
		return r1 * r2;
	}
	
}
