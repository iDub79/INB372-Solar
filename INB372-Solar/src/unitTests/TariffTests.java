package unitTests;

import solar.TariffCalculation;
import solar.TariffException;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * @author  Franco
 *
 */
public class TariffTests {
	
	private TariffCalculation tariff;
	
	/*
	 * Constructor() test
	 */
	//@Test (expected = TariffCalculation.class)
	//Test for negative electric
	public void constructor_tariffCalculation() throws TariffException {
		// negative
		//new TariffCalculation (null, -1);
	}

	/*
	 * calAnnualElectric() test
	 */
	@Test
	// Test if method return correct data.
	public void runcorrect_calAnnualElectric() throws TariffException {
		//tariff = new TariffCalculation(1.2);
		assertEquals(tariff.calAnnualElectric(), (double)438,2);
	}
	
	/*
	 * calAnnualSaving() test
	 */
	@Test
	// Test if method return correct data.
	public void calAnnualSaving() throws TariffException {
		//tariff = new TariffCalculation (1.2);
		assertEquals(tariff.calAnnualSaving(), (double)192.72,2);
	}
}
