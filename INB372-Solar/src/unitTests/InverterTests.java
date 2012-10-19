package unitTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import components.Inverter;
import exceptions.InverterException;


public class InverterTests {
	
	private final String MANUFACTURER = "Manufacturer";
	private final String MODEL = "Model";
	private final float EFFICIENCY = 90.5f; 
	
	private final String EMPTYMANUFACTURER = "";
	private final String EMPTYMODEL = "";
	private final float NEGEFFICIENCY = -1f;
	
	private Inverter validInverter;

	@Before
	@Test
	public void init() throws InverterException {
		validInverter = new Inverter(MANUFACTURER, MODEL, EFFICIENCY);
	}
	
	@Test
	public void getInverterManufacturerTest() throws InverterException {		
		assertTrue(validInverter.getManufacturer().equals(MANUFACTURER));
	}
	
	@Test
	public void getInverterModelTest() throws InverterException {		
		assertTrue(validInverter.getModel().equals(MODEL));
	}
	
	@Test
	public void getInverterEfficiencyTest() throws InverterException {		
		assertEquals(validInverter.getEfficiency(), EFFICIENCY, 2);
	}
	
	@Test
	public void setInverterManufacturerTest() throws InverterException {
		String newInverterManufacturer = "New Manufacturer";
		validInverter.setManufacturer(newInverterManufacturer);
		assertEquals(validInverter.getManufacturer(), newInverterManufacturer);
	}
	
	@Test
	public void setInverterModelTest() throws InverterException {
		String newInverterModel = "New Model";
		validInverter.setModel(newInverterModel);
		assertEquals(validInverter.getModel(), newInverterModel);
	}
	
	@Test
	public void setInverterPowerTest() throws InverterException {
		float newInverterEfficiency = 10;
		validInverter.setEfficiency(newInverterEfficiency);
		assertEquals(validInverter.getEfficiency(), newInverterEfficiency, 2);
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
