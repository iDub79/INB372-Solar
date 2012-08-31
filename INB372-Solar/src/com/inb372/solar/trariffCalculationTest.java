package com.inb372.solar;
import static org.junit.Assert.*;

import org.junit.Test;



/**
 * @author Franco
 *
 */
public class trariffCalculationTest {
	
	private trariffCalculation trariff;
	
	/*
	 * Constructor() test
	 */
	@Test (expected = trariffException.class)
	//Test for negative electric
	public void constructor_trariffCalculation() throws trariffException {
		// negative
		new trariffCalculation (-1);
	}

	/*
	 * calAnnualElectric() test
	 */
	@Test
	// Test if method return correct data.
	public void runcorrect_calAnnualElectric() throws trariffException {
		trariff = new trariffCalculation (1.2);
		assertEquals(trariff.calAnnualElectric(), (double)438,2);
	}
	
	/*
	 * calAnnualSaving() test
	 */
	@Test
	// Test if method return correct data.
	public void calAnnualSaving() throws trariffException {
		trariff = new trariffCalculation (1.2);
		assertEquals(trariff.calAnnualSaving(), (double)192.72,2);
	}
	
	
}
