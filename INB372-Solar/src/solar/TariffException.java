package solar;

public class TariffException extends Exception {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 * Inherited constructor from superclass <code>Exception</code>
	 * 
	 */
	public TariffException() {
		super(); 
	}

	
	/**
	 * 
	 * Class specific constructor 
	 * 
	 * @param msg - specific error message
	 */
	public TariffException(String msg) {
		super("TariffException: " + msg);
	}
}