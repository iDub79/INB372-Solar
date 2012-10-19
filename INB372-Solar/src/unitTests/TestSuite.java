package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	PanelTests.class,
	PanelControllerTests.class,
	InverterTests.class,
	InverterControllerTests.class,
	DataProviderTests.class,
	CalculatorTests.class,	
	TariffTests.class,
	TariffRuleTests.class,
	TariffRuleListTests.class,
	SolarServletTests.class,
	SunPositionTests.class,
	DistanceCalcTests.class
})


public class TestSuite {

}
