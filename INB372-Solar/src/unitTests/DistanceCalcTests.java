package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import solar.DistanceCalc;

public class DistanceCalcTests {

	
	
	@Test
	public void normalCloseBrisban() {
		DistanceCalc brisClose = new DistanceCalc();
		float morayfieldLat = -27.105453f;
		float morayfieldLon = 152.948145f;
		float[] closestSunTable = brisClose.findClosestStation(morayfieldLat, morayfieldLon);
		assert(closestSunTable[0] == 3.3f);
		
	}
	
	@Test
	public void normalClosePerth() {
		DistanceCalc brisClose = new DistanceCalc();
		float northamLat = -31.65453f;
		float northamLon = 116.670517f;
		float[] closestSunTable = brisClose.findClosestStation(northamLat, northamLon);
		assert(closestSunTable[0] == 9.4f);
	}

}
