package unitTests;

import org.junit.Before;
import org.junit.Test;

import components.PanelController;

public class PanelControllerTests {

	private PanelController ctrl;
	
	@Before
	@Test
	public void init() {
		ctrl = new PanelController();
	}
}
