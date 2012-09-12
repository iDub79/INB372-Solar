package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	CalculatorTest.class,	
	SolarSystemInfoTests.class,
	TariffTests.class,
	SolarServletTests.class,
	PanelServletTests.class,
	InverterServletTests.class
})


public class TestSuite {

}
