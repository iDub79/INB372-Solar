package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import solar.SolarSystemInfo;

import solar.SolarSystemException;

public class SolarSystemInfoTest {

	@Test
	public void vanillaConstructorTest() throws SolarSystemException {
		SolarSystemInfo vanillaSystem = new SolarSystemInfo(4,200,90,45,20);
		assertTrue(vanillaSystem.getSizeOfPanels() == 4 &&
				vanillaSystem.getWattRating() == 200 &&
				vanillaSystem.getInverterEffeciency() == 90 &&
				vanillaSystem.getPanelAngle() == 45 &&
				vanillaSystem.getDayTimePowerConsumption() == 20);
	}
	
	@Test(expected = SolarSystemException.class)
	public void negativePanelSizeTest() throws SolarSystemException
	{
		SolarSystemInfo negativePanelSize = new SolarSystemInfo(-1,200,90,45,20);
	}

	@Test(expected = SolarSystemException.class)
	public void negativeWattRatingTest() throws SolarSystemException
	{
		SolarSystemInfo negativeWattRating = new SolarSystemInfo(4,-1,90,45,20);
	}
	
	@Test(expected = SolarSystemException.class)
	public void negativeInverterEffeciencyTest() throws SolarSystemException
	{
		SolarSystemInfo negativeInverterEffeciency = new SolarSystemInfo(4,200,-1,45,20);
	}

	@Test(expected = SolarSystemException.class)
	public void negativePanelAngleTest() throws SolarSystemException
	{
		SolarSystemInfo negativePanelAngle = new SolarSystemInfo(4,200,90,-1,20);
	}
	
	@Test(expected = SolarSystemException.class)
	public void negativePowerConsumptionTest() throws SolarSystemException
	{
		SolarSystemInfo negativePowerConsumption = new SolarSystemInfo(4,200,90,45,-1);
	}
	
	@Test(expected = SolarSystemException.class)
	public void oversizedPanelTest() throws SolarSystemException
	{
		SolarSystemInfo oversizedPanel = new SolarSystemInfo(8,200,90,45,20);
	}
	
	@Test(expected = SolarSystemException.class)
	public void wattRatingTooHighTest() throws SolarSystemException
	{
		SolarSystemInfo wattRatingTooHigh = new SolarSystemInfo(4,400,90,45,20);
	}

}
