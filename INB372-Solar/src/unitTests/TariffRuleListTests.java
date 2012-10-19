package unitTests;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import solar.TariffRule;
import solar.TariffRuleList;

public class TariffRuleListTests {

	private TariffRuleList list;
	
	@Before
	@Test
	public void init() {
		list = new TariffRuleList();
	}
	
	@Test
	public void getRulesTest() {
		ArrayList<TariffRule> rules = list.getrules();
		String state = rules.get(0).getState();
		assertTrue(state.equals("Victoria"));
	}
	
	@Test
	public void getRuleForStateTest() {
		String stateToRetrieve = "Victoria";
		ArrayList<TariffRule> rules = list.getRuleForState(stateToRetrieve);
		double tariffRate = rules.get(0).getTariffRate();
		assertEquals(tariffRate, 0.08, 2);
	}
}
