package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import components.Inverter;
import components.Panel;

import exceptions.CalculatorException;
import exceptions.InverterException;
import exceptions.PanelException;

import solar.Calculator;
import solar.DistanceCalc;


public class CalculatorTests {

	private Calculator testCalc;	
	
	@Before
	@Test
	public void initCalculator() throws PanelException, InverterException, CalculatorException {
		Panel testPanel = new Panel("panel1", "panelCompany", 300);
		Inverter testInverter = new Inverter("invert1", "inverterCompany", 90);
		
		testCalc = new Calculator(testPanel, testInverter, 2, 4, 90, -42.881903f, 147.323815f);
		assertEquals(testCalc.calcDailyPower(), 2, 2);
	}	
	
	@Test
	public void calcDailyExcessTest() throws CalculatorException {
		assertEquals(testCalc.calcDailyExcess(), -3.215176582336426, 2);		
	}
	
	@Test
	public void makeDailyGenTableTest() throws CalculatorException {
		float[] table = testCalc.makeDailyGenTable();
		assertEquals(table[0], 2, 2);
	}
	
	
}