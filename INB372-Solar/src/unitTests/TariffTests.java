package unitTests;



import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import exceptions.CalculatorException;
import exceptions.SolarSystemException;
import exceptions.TariffException;

import solar.Calculator;
import solar.SolarSystemInfo;
import solar.TariffCalculation;

public class TariffTests {
	
	private TariffCalculation tariff;
	private SolarSystemInfo runCorrectSystemInfo;
	private Calculator runCorrectCalculator;
	
	private final int PANELSIZE = 1000;
	private final int PANELRATING = 2000;
	private final int INVERTOREFFICIENCY = 90;
	private final int PANELANGLE = 45;
	private final int CONSUMPTION = 3;
	
	@Before
	public void initialise() throws SolarSystemException, CalculatorException, TariffException {		
		runCorrectSystemInfo = new SolarSystemInfo(PANELSIZE, PANELRATING, INVERTOREFFICIENCY, PANELANGLE, CONSUMPTION);
		runCorrectCalculator = new Calculator(runCorrectSystemInfo);
		tariff = new TariffCalculation(runCorrectCalculator, 0.8f);
	}
	


	@Test
	// Test if method return correct data.
	public void runcorrect_calAnnualElectric() throws TariffException {
		assertEquals(tariff.calAnnualElectric(), 339.59, 2);
	}
	

	@Test
	// Test if method return correct data.
	public void calAnnualSaving() throws TariffException {
		assertEquals(tariff.calAnnualSaving(), 14942.09, 2);
	}
}
