package exceptions;

public class TariffException extends Exception {

	private static final long serialVersionUID = 1L;

	public TariffException(String msg) {
		super("TariffException: " + msg);
	}
}