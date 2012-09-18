package unitTests;

import solar.*;
import components.*;
import exceptions.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SolarServletTests {

	private TariffCalculation tariff;
	private float tariffAmount = 0.44f;
	private Panel panel;
	private Inverter inverter;
	private Calculator calcNew;
	private float power = 200;
	private Integer panelQty = 1;
	private float consumption = 1;
	private float inverterEff = 90;
	private float panelAngle = 45;
	
	@Before
	@Test
	public void initialise() throws PanelException, InverterException {
		panel = new Panel("Panel Manufacturer", "Panel Model", power);
		inverter = new Inverter("Inverter Manufacturer", "Inverter Model", inverterEff);
	}
	
	@Test
	public void testCreateCalculator() throws CalculatorException {
		calcNew = new Calculator(panel, inverter, panelQty, consumption, panelAngle);
		//assert(createCalculator(), calc.equals(calcNew));
	}
	
	@Test
	public void testCreateTariff() throws TariffException, CalculatorException {		
		//tariff = new TariffCalculation(calcNew, tariffAmount);
	}
}
