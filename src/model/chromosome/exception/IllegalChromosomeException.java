package model.chromosome.exception;

public class IllegalChromosomeException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	
	public IllegalChromosomeException(String s) {
		super(s);
	}
	
	public IllegalChromosomeException(Class<?> currentChromosome, Class<?> expectedChromosome) {
		super(extractName(currentChromosome) + " no puede llevar a cabo la acción como si fuera un " + extractName(expectedChromosome));
	}

	private static String extractName(Class<?> s) {
		String[] name = s.toString().split("\\.");
		
		return name[name.length - 1];
	}
}
