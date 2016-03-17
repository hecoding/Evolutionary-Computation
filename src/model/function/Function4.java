package model.function;

import java.util.ArrayList;

public class Function4 extends Function {

	public Function4(int n) {
		this.name = "función 4";
		this.minimization = true;
		this.varNum = n;
		this.limits = new ArrayList<Limit>(this.varNum);
		
		Limit limit = new Limit(0, Math.PI);
		for (int i = 0; i < this.varNum; i++)
			this.limits.add(limit);
	}

	@Override
	public double f(ArrayList<Double> params) {
		double res = 0;
		
		for (int i = 1; i <= this.varNum; i++) {
			res += Math.sin(params.get(i-1)) * Math.pow(Math.sin(( ((i + 1) * Math.pow(params.get(i-1),2)) / Math.PI )), 20);
		}
		
		return -res;
	}

}
