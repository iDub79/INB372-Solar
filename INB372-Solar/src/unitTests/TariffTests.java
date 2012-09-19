package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import components.Inverter;
import components.Panel;

import exceptions.*;

import solar.Calculator;
import solar.TariffCalculation;

public class TariffTests {

	private Panel panel;
	private final String PMANUFACTURER = "ABC";
	private final String PMODEL = "CBA";
	private final int PANELRATING = 500;

	private Inverter inverter;
	private final String IMANUFACTURER = "DEF";
	private final String IMODEL = "FED";
	private final int INVERTOREFFICIENCY = 100;

	private Calculator calculator;
	private final int CONSUMPTION = 1;
	private final int PANELANGLE = 45;

	private TariffCalculation tariff;
	private final float TARIFFVALUE = 1;

	@Before
	@Test
	public void initialise() throws CalculatorException, TariffException, PanelException, InverterException {
		panel = new Panel(PMANUFACTURER, PMODEL, PANELRATING);
		inverter = new Inverter(IMANUFACTURER, IMODEL, INVERTOREFFICIENCY);
		calculator = new Calculator(panel, inverter, 1, CONSUMPTION, PANELANGLE);
		tariff = new TariffCalculation(calculator, TARIFFVALUE);
	}

	@Test
	// Test if method return correct data.
	public void testCalAnnualElectric() throws TariffException {
		assertEquals(tariff.calAnnualElectric(), 2.833, 2);
	}

	@Test
	// Test if method return correct data.
	public void testCalAnnualSaving() throws TariffException {
		assertEquals(tariff.calAnnualSaving(), 3.148, 2);
	}
}