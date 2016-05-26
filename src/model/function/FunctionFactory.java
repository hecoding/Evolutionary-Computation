package model.function;

public class FunctionFactory {
 	private static FunctionFactory instance;
 	
	public static FunctionFactory getInstance() {
		if (instance == null){
			instance = new FunctionFactory();
		}
		return instance;
	}
	
	public Function create(String id) {
		if (id == "function 1")
			return new Function1();
		else if (id == "function 2")
			return new Function2();
		else if (id == "function 3")
			return new Function3();
		else if (id == "function 5")
			return new Function5();
		else
			throw new IllegalArgumentException("Unknown function");
	}
	
	public Function createFunc4(int n, boolean b) {
		return new Function4(n, b);
	}
	
}
