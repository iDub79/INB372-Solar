package exceptions;

public class CalculatorException extends Exception {

	private static final long serialVersionUID = 1L;

	public CalculatorException() {
	}

	public CalculatorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CalculatorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CalculatorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}