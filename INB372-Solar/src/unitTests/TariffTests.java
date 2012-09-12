package unitTests;

import solar.Calculator;
import solar.CalculatorException;
import solar.SolarSystemException;
import solar.SolarSystemInfo;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * @author  Franco
 *
 */
public class TariffTests {
	
	private TariffCalculation tariff;
	
	SolarSystemInfo runCorrectSystemInfo;
	Calculator runCorrectCalculator;
	runCorrectSystemInfo = new SolarSystemInfo(400, 10, 90, 40, 4);
	runCorrectCalculator = new Calculator(runCorrectSystemInfo);
	runCorrectTariff = new TariffCalculation(runCorrectCalculator,0.44f);
	
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
		assertEquals(tariff.calAnnualElectric(), (double)339.59,2);
	}
	
	/*
	 * calAnnualSaving() test
	 */
	@Test
	// Test if method return correct data.
	public void calAnnualSaving() throws TariffException {
		assertEquals(tariff.calAnnualSaving(), (double)14942.09,2);
	}
}
