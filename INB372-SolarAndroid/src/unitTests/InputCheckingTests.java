package unitTests;

import org.junit.*;
import static org.junit.Assert.*;


import solar.solarAndroid.InputChecking;
import solar.solarAndroid.InvalidInputException;

public class InputCheckingTests {

	
	@Test(expected=InvalidInputException.class)
	public void testGetPanelEfficiencyNullString() throws InvalidInputException {
		InputChecking.getPanelEfficiency(null);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetPanelEfficiencyEmptyString() throws InvalidInputException {
		InputChecking.getPanelEfficiency("");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetPanelEfficiencyNotFloat() throws InvalidInputException {
		InputChecking.getPanelEfficiency("abc");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetPanelEfficiencyLessThanZero() throws InvalidInputException {
		InputChecking.getPanelEfficiency("-1");
	}
	
	
	@Test
	public void testGetPanelEfficiency() throws InvalidInputException {
		assertEquals((Float)3.0f, InputChecking.getPanelEfficiency("3"));
	}

	
	@Test(expected=InvalidInputException.class)
	public void testGetInverterEfficiencyNullString() throws InvalidInputException {
		InputChecking.getInverterEfficiency(null);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetInverterEfficiencyEmptyString() throws InvalidInputException {
		InputChecking.getInverterEfficiency("");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetInverterEfficiencyNotFloat() throws InvalidInputException {
		InputChecking.getInverterEfficiency("abc");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetInverterEfficiencyLessThanZero() throws InvalidInputException {
		InputChecking.getInverterEfficiency("-1");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetInverterEfficiencyTooLow() throws InvalidInputException {
		InputChecking.getInverterEfficiency("25");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetInverterEfficiencyTooLowDecimal() throws InvalidInputException {
		InputChecking.getInverterEfficiency("0.25");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetInverterEfficiencyTooBig() throws InvalidInputException {
		InputChecking.getInverterEfficiency("105");
	}
	
	@Test
	public void testGetInverterEfficiencyPercent() throws InvalidInputException {
		assertEquals((Float)95f, InputChecking.getInverterEfficiency("95"));
	}
	
	@Test
	public void testGetInverterEfficiencyDecimal() throws InvalidInputException {
		assertEquals((Float)95f, InputChecking.getInverterEfficiency("0.95"));
	}

	
	@Test(expected=InvalidInputException.class)
	public void testGetTariffNullString() throws InvalidInputException {
		InputChecking.getTariff(null);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetTariffEmptyString() throws InvalidInputException {
		InputChecking.getTariff("");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetTariffNotFloat() throws InvalidInputException {
		InputChecking.getTariff("abc");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetTariffLessThanZero() throws InvalidInputException {
		InputChecking.getTariff("-1");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetTariffHuge() throws InvalidInputException {
		InputChecking.getTariff("2000");
	}
	
	@Test
	public void testGetTariffCents() throws InvalidInputException {
		assertEquals((Float)0.08f, InputChecking.getTariff("8"));
	}
	
	@Test
	public void testGetTariffDollars() throws InvalidInputException {
		assertEquals((Float)0.44f, InputChecking.getTariff("0.44"));
	}

	@Test(expected=InvalidInputException.class)
	public void testGetConsumptionNullString() throws InvalidInputException {
		InputChecking.getConsumption(null);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetConsumptionEmptyString() throws InvalidInputException {
		InputChecking.getConsumption("");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetConsumptionNotFloat() throws InvalidInputException {
		InputChecking.getConsumption("abc");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetConsumptionLessThanZero() throws InvalidInputException {
		InputChecking.getConsumption("-1");
	}
	
	@Test
	public void testGetConsumption() throws InvalidInputException {
		assertEquals((Float)3.0f, InputChecking.getConsumption("3"));
	}

	
	@Test(expected=InvalidInputException.class)
	public void testGetAngleNullString() throws InvalidInputException {
		InputChecking.getAngle(null);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetAngleEmptyString() throws InvalidInputException {
		InputChecking.getAngle("");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetAngleNotFloat() throws InvalidInputException {
		InputChecking.getAngle("abc");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetAngleNegative() throws InvalidInputException {
		InputChecking.getAngle("-45");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetAngleTooBig() throws InvalidInputException {
		InputChecking.getAngle("145");
	}
	
	@Test
	public void testGetAngle() throws InvalidInputException {
		assertEquals((Float)45.0f, InputChecking.getAngle("45"));
	}

	
	@Test(expected=InvalidInputException.class)
	public void testGetOrientationNullString() throws InvalidInputException {
		InputChecking.getOrientation(null);
	}

	@Test(expected=InvalidInputException.class)
	public void testGetOrientationEmptyString() throws InvalidInputException {
		InputChecking.getOrientation("");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOrientationNotFloat() throws InvalidInputException {
		InputChecking.getOrientation("abc");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOrientationGreaterThan360() throws InvalidInputException {
		InputChecking.getOrientation("380");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOrientationLessThanZero() throws InvalidInputException {
		 InputChecking.getOrientation("-45");
	}
	
	@Test
	public void testGetOrientation() throws InvalidInputException {
		assertEquals("45.0", InputChecking.getOrientation("45"));
	}
	
	// Tests null string for address throws exception
	@Test(expected=InvalidInputException.class)
	public void testGetAddressNullString() throws InvalidInputException {
		InputChecking.getAddress(null);
	}
	
	// Eventually would make a comprehensive address check that actually looks at how address should look
	@Test
	public void testGetAddress() throws InvalidInputException {
		assertTrue("stuffs" == InputChecking.getAddress("stuffs"));
	}


	// Tests null string for panel quantity throws exception
	@Test(expected=InvalidInputException.class)
	public void testGetPanelQuantityNullString() throws InvalidInputException {
		InputChecking.getPanelQuantity(null);
		//fail("Should have thrown an InvalidInputException");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetPanelQuantityEmptyString() throws InvalidInputException {
		InputChecking.getPanelQuantity("");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetPanelQuantityNotInteger() throws InvalidInputException {
		InputChecking.getPanelQuantity("abc");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetPanelQuantityLessThanZero() throws InvalidInputException {
		InputChecking.getPanelQuantity("-1");
	}
	
	
	public void testGetPanelQuantity() throws InvalidInputException {
		assertEquals((Integer)5, InputChecking.getPanelQuantity("5"));
	}

	// Tests null string for inverter manufacturer throws exception
	@Test(expected=InvalidInputException.class)
	public void testGetInverterManufacturerNullString() throws InvalidInputException {
		InputChecking.getInverterManufacturer(null);
	}
	
	
	public void testGetInverterManufacturer() throws InvalidInputException {
		assertTrue("stuffs" == InputChecking.getInverterManufacturer("stuffs"));
	}

	// Tests null string for panel manufacturer throws exception
	@Test(expected=InvalidInputException.class)
	public void testGetPanelManufacturerNullString() throws InvalidInputException {
		InputChecking.getPanelManufacturer(null);
	}
	
	
	public void testGetPanelManufacturer() throws InvalidInputException {
		assertTrue("stuffs" == InputChecking.getPanelManufacturer("stuffs"));
	}
	
	// Tests null string for inverter model throws exception
	@Test(expected=InvalidInputException.class)
	public void testGetInverterModelNullString() throws InvalidInputException {
		InputChecking.getInverterModel(null);
	}
	
	
	public void testGetInverterModel() throws InvalidInputException {
		assertTrue("stuffs" == InputChecking.getInverterModel("stuffs"));
	}

	// Tests null string for panel model throws exception
	@Test(expected=InvalidInputException.class)
	public void testGetPanelModelNullString() throws InvalidInputException {
		InputChecking.getPanelModel(null);
	}
	
	@Test
	public void testGetPanelModel() throws InvalidInputException {
		assertTrue("stuffs" == InputChecking.getPanelModel("stuffs"));
	}

}
