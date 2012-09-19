package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	PanelTests.class,
	InverterTests.class,
	CalculatorTests.class,	
	TariffTests.class,
	SolarServletTests.class,
	SunPositionTests.class
	//PanelServletTests.class,
	//InverterServletTests.class
})


public class TestSuite {

}
