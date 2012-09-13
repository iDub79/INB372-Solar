package unitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.Panel;
import exceptions.PanelException;

public class PanelTests {

	private final String MANUFACTURER = "Manufacturer";
	private final String MODEL = "Model";
	private final float POWER = 90.5f; 
	
	private final String EMPTYMANUFACTURER = "";
	private final String EMPTYMODEL = "";
	private final float NEGPOWER = -1f;

	
	@Test
	public void validPanelTest() throws PanelException {
		Panel validPanel = new Panel(MANUFACTURER, MODEL, POWER);
		assertTrue(validPanel.getManufacturer().equals(MANUFACTURER) && validPanel.getModel().equals(MODEL) && validPanel.getPower() == POWER);
	}
	
	@Test(expected = PanelException.class)
	public void emptyPanelManufacturerTest() throws PanelException {
		new Panel(EMPTYMANUFACTURER, MODEL, POWER);
	}
	
	@Test(expected = PanelException.class)
	public void emptyPanelModelTest() throws PanelException {
		new Panel(MANUFACTURER, EMPTYMODEL, POWER);
	}
	
	@Test(expected = PanelException.class)
	public void negativePanelEffeciencyTest() throws PanelException {
		new Panel(MANUFACTURER, MODEL, NEGPOWER);
	}
}
