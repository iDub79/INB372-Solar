package unitTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import solar.TariffRule;

public class TariffRuleTests {

	private final String STATE = "QLD";
	private final String DESCRIPTION = "Description";
	private final Boolean GROSS = true;
	private final double TARIFFRATE = 1.1;
	
	private TariffRule rule;
	
	@Before
	@Test
	public void init() {
		rule = new TariffRule(STATE, DESCRIPTION, GROSS, TARIFFRATE);
	}
	
	@Test
	public void getStateTest() {		
		assertTrue(rule.getState().equals(STATE));
	}
	
	@Test
	public void getDescriptionTest() {		
		assertTrue(rule.getDescription().equals(DESCRIPTION));
	}
	
	@Test
	public void getGrossTest() {		
		assertEquals(rule.getGross(), GROSS);
	}
	
	@Test
	public void getTariffRateTest() {		
		assertEquals(rule.getTariffRate(), TARIFFRATE, 2);
	}
	
	@Test
	public void setStateTest() {
		String newState = "New State";
		rule.setState(newState);
		assertEquals(rule.getState(), newState);
	}
	
	@Test
	public void setDescriptionTest() {
		String newDescription = "New Description";
		rule.setDescription(newDescription);
		assertEquals(rule.getDescription(), newDescription);
	}
	
	@Test
	public void setGrossTest() {
		Boolean newGross = false;
		rule.setGross(newGross);
		assertEquals(rule.getGross(), newGross);
	}
	
	@Test
	public void setTariffRateTest() {
		double newTariffRate = 10;
		rule.setTariffRate(newTariffRate);
		assertEquals(rule.getTariffRate(), newTariffRate, 2);
	}
}
