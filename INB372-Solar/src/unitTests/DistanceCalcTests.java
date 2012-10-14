package unitTests;

import static org.junit.Assert.*;
import org.junit.Test;

import components.Inverter;
import components.Panel;

import exceptions.CalculatorException;
import exceptions.InverterException;
import exceptions.PanelException;

import solar.Calculator;
import solar.DistanceCalc;

public class DistanceCalcTests {

	@Test
	public void BrisbaneCalculatorOutput() {
		try {
			Panel testPanel = new Panel("panel1", "panelCompany", 300);
			Inverter testInverter = new Inverter("invert1", "inverterCompany", 90);
			float morayfieldLat = -27.105453f;
			float morayfieldLon = 152.948145f;
			Calculator testCalc = new Calculator(testPanel, testInverter, 2, 4, 90, -34.928621f, 138.599959f);
			testCalc.calcDailyExcess();
			testCalc.makeDailyGenTable();
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
	
	@Test
	public void normalCloseBrisban() {
		DistanceCalc brisClose = new DistanceCalc();
		double morayfieldLat = -27.105453;
		double morayfieldLon = 152.948145;
		double[] closestSunTable = brisClose.findClosestStation(morayfieldLat, morayfieldLon);
		assert(closestSunTable[0] == 3.3);
		
	}
	
	@Test
	public void normalClosePerth() {
		DistanceCalc brisClose = new DistanceCalc();
		double northamLat = -31.65453;
		double northamLon = 116.670517;
		double[] closestSunTable = brisClose.findClosestStation(northamLat, northamLon);
		assert(closestSunTable[0] == 9.4);
	}

}
