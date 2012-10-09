package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import solar.Calculator;
import solar.CalculatorException;
import solar.SolarSystemException;
//import solar.SolarSystemInfo;

import static java.lang.Math.*;

public class CalculatorTest {

	@Test
	public void nullSystemInConstructor() {
		try {
			Calculator nullSystem = new Calculator(null, null, 0, 0, 0);
			fail("Exception not thrown");

		}
		catch (exceptions.CalculatorException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void zeroPanelSize() {
//		//SolarSystemInfo panelSizeZero;
//		try {
//			panelSizeZero = new SolarSystemInfo(0.0f, 600, 50, 40, 7);
//			
//			if (panelSizeZero.getSizeOfPanels() != 0.0f) {
//				fail("size not zero");
//			}
//			try {
//				Calculator zeroCalc = new Calculator(panelSizeZero);
//				zeroCalc.calcDailyPower();
//				fail("exception not thrown");
//			}
//			catch (CalculatorException e) {
//				e.printStackTrace();
//			}
//		}
//		catch (SolarSystemException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}		
	}

	@Test
	public void normalInputOutput() {
//		SolarSystemInfo panelSizeZero;
//		try {
//			panelSizeZero = new SolarSystemInfo(6, 600, 0.5f, 40, 7);
//			
//			Calculator zeroCalc;
//			try {
//				zeroCalc = new Calculator(panelSizeZero);
//				assert (abs(zeroCalc.calcDailyPower() - 62.0f) < .01f);
//			}
//			catch (CalculatorException e) {
//				fail("reason Unkown");
//				e.printStackTrace();
//			}
//		}
//		catch (SolarSystemException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}		
	}
}