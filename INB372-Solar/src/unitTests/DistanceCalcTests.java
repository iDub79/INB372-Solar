package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import list;
import awt.geom.Point2d.Double;

import CalcDistance.java;

public class DistanceCalcTests {

	
	
	@Test
	public void normal() {
		List <Point2D.Double> capitalCities = new list();
		// Brisbane
		capitalCities.add(Point2D.Double(-27.471011, 153.023449));
		// Perth
		capitalCities.add(Point2D.Double(-31.932854, 115.86194));
		// Adelaide
		capitalCities.add(Point2D.Double(-34.928621, 138.599959));
		// Hobart
		capitalCities.add(Point2D.Double(-42.881903, 147.323815));
		// Darwin
		capitalCities.add(Point2D.Double(--12.46282, 130.841769));
		// Melbourne
		capitalCities.add(Point2D.Double(-37.811367, 144.971829));
		// Canberra
		capitalCities.add(Point2D.Double(-35.308235, 149.124224));
		// Sydney
		capitalCities.add(Point2D.Double(-33.867487, 151.20699));
		
		
		assertEquals(capitalCities.get(0).getX(), -27.471011)
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
