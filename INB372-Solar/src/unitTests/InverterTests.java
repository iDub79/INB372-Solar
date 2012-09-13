package unitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.Inverter;
import exceptions.InverterException;

public class InverterTests {
	
	private final String MANUFACTURER = "Manufacturer";
	private final String MODEL = "Model";
	private final float EFFICIENCY = 90.5f; 
	
	private final String EMPTYMANUFACTURER = "";
	private final String EMPTYMODEL = "";
	private final float NEGEFFICIENCY = -1f;

	
	@Test
	public void validInverterTest() throws InverterException {
		Inverter validInverter = new Inverter(MANUFACTURER, MODEL, EFFICIENCY);
		assertTrue(validInverter.getManufacturer().equals(MANUFACTURER) && validInverter.getModel().equals(MODEL) && validInverter.getEfficiency() == EFFICIENCY);
	}
	
	@Test(expected = InverterException.class)
	public void emptyInverterManufacturerTest() throws InverterException {
		new Inverter(EMPTYMANUFACTURER, MODEL, EFFICIENCY);
	}
	
	@Test(expected = InverterException.class)
	public void emptyInverterModelTest() throws InverterException {
		new Inverter(MANUFACTURER, EMPTYMODEL, EFFICIENCY);
	}
	
	@Test(expected = InverterException.class)
	public void negativeInverterEffeciencyTest() throws InverterException {
		new Inverter(MANUFACTURER, MODEL, NEGEFFICIENCY);
	}
}
