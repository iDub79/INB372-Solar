package exceptions;

public class SolarSystemException extends Exception {

	private static final long serialVersionUID = 1L;

	public SolarSystemException() {
	}

	public SolarSystemException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SolarSystemException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public SolarSystemException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
