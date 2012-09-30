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

import static java.lang.Math.*;

public class CalculatorTests {

	//@Test
	public void printLnTests() {
		try {
			Panel testPanel = new Panel("panel1", "panelCompany", 300);
			Inverter testInverter = new Inverter("invert1", "inverterCompany", 90);
			Calculator testCalc = new Calculator(testPanel, testInverter, 2, 4, 90);
			testCalc.calcDailyExcess();
		}
		catch (PanelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InverterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (CalculatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void basicTestMonthlyTable() {
		try {
			Panel testPanel = new Panel("panel1", "panelCompany", 300);
			Inverter testInverter = new Inverter("invert1", "inverterCompany", 90);
			Calculator testCalc = new Calculator(testPanel, testInverter, 2, 4, 90);
			testCalc.makeMonthlyGenTable();
		}
		catch (PanelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InverterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (CalculatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/*
	 * @Test public void nullSystemInConstructor() { try { Calculator nullSystem
	 * = new Calculator(null); fail("Exception not thrown"); ======= >>>>>>>
	 * refs/remotes/origin/master
	 * 
	 * @Test (expected = CalculatorException.class) public void
	 * nullSystemInConstructor() throws CalculatorException { Calculator
	 * nullSystem = new Calculator(null, null, null, 0, 0); } <<<<<<< HEAD
	 * 
	 * 
	 * 
	 * @Test public void normalInputOutput() { SolarSystemInfo panelSizeZero;
	 * try { panelSizeZero = new SolarSystemInfo(6, 600, 0.5f, 40, 7);
	 * Calculator zeroCalc; try { zeroCalc = new Calculator(null, null, null);
	 * assert (abs(zeroCalc.calcDailyPower() - 62.0f) < .01f); } catch
	 * (CalculatorException e) { fail("reason Unkown"); e.printStackTrace(); } }
	 * catch (SolarSystemException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); } }
	 */
}