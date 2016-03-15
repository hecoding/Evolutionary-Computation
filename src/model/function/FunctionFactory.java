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
		if (id == "función 1")
			return new Function1();
		if (id == "función 2")
			return new Function2();
		if (id == "función 3")
			return new Function3();
		// complete
		else
			throw new IllegalArgumentException("Unknown function");
	}
	
}
