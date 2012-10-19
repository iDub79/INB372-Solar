package unitTests;

import org.junit.Before;
import org.junit.Test;

import components.InverterController;

public class InverterControllerTests {

	private InverterController ctrl;
	
	@Before
	@Test
	public void init() {
		ctrl = new InverterController();
	}
}
