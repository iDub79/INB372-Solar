package unitTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import components.Panel;
import exceptions.PanelException;

public class PanelTests {

	private final String MANUFACTURER = "Manufacturer";
	private final String MODEL = "Model";
	private final float POWER = 90.5f; 
	
	private final String EMPTYMANUFACTURER = "";
	private final String EMPTYMODEL = "";
	private final float NEGPOWER = -1f;
	
	private Panel validPanel;

	@Before
	@Test
	public void init() throws PanelException {
		validPanel = new Panel(MANUFACTURER, MODEL, POWER);
	}
	
	@Test
	public void getPanelManufacturerTest() throws PanelException {		
		assertTrue(validPanel.getManufacturer().equals(MANUFACTURER));
	}
	
	@Test
	public void getPanelModelTest() throws PanelException {		
		assertTrue(validPanel.getModel().equals(MODEL));
	}
	
	@Test
	public void getPanelPowerTest() throws PanelException {		
		assertEquals(validPanel.getPower(), POWER, 2);
	}
	
	@Test
	public void setPanelManufacturerTest() throws PanelException {
		String newPanelManufacturer = "New Manufacturer";
		validPanel.setManufacturer(newPanelManufacturer);
		assertEquals(validPanel.getManufacturer(), newPanelManufacturer);
	}
	
	@Test
	public void setPanelModelTest() throws PanelException {
		String newPanelModel = "New Model";
		validPanel.setModel(newPanelModel);
		assertEquals(validPanel.getModel(), newPanelModel);
	}
	
	@Test
	public void setPanelPowerTest() throws PanelException {
		float newPanelPower = 10;
		validPanel.setPower(newPanelPower);
		assertEquals(validPanel.getPower(), newPanelPower, 2);
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
