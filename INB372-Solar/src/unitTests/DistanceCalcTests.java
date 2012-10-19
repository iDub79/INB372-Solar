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
	public void normalCloseBrisban() {
		DistanceCalc brisClose = new DistanceCalc();
		double morayfieldLat = -27.105453;
		double morayfieldLon = 152.948145;
		double[] closestSunTable = brisClose.findClosestStation(morayfieldLat, morayfieldLon);
		assertEquals(closestSunTable[0], 3.3, 2);		
	}
	
	@Test
	public void normalClosePerth() {
		DistanceCalc brisClose = new DistanceCalc();
		double northamLat = -31.65453;
		double northamLon = 116.670517;
		double[] closestSunTable = brisClose.findClosestStation(northamLat, northamLon);
		assertEquals(closestSunTable[0], 9.4, 2);
	}

}
