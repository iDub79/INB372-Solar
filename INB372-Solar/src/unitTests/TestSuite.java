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
	//PanelServletTests.class,
	//InverterServletTests.class,
	SunPositionTests.class,
	DistanceCalcTests.class
})


public class TestSuite {

}
