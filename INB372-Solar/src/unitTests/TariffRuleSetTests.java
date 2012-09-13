/**
 * 
 */
package unitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import solar.TariffRuleSet;

/**
 * @author Ellaria
 *
 */
public class TariffRuleSetTests {

	TariffRuleSet defaultRule;
	TariffRuleSet basicRule;
	TariffRuleSet oddArrayRule;
	TariffRuleSet evenArrayRule;
	TariffRuleSet oddListRule;
	TariffRuleSet evenListRule;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		defaultRule = new TariffRuleSet();
		basicRule = new TariffRuleSet(0.44);
		oddArrayRule = new TariffRuleSet(new double[]{0.44, 0.07}, new double[]{5});
		evenArrayRule = new TariffRuleSet(new double[]{0.44, 0.07}, new double[]{0, 5});
		oddListRule = new TariffRuleSet(0.44, 5, 0.07);
		evenListRule = new TariffRuleSet(0, 0.44, 5, 0.07);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefaultRule() {
		assertEquals(4.40, defaultRule.calculateDaysTariffReturn(10), 0.001);
	}
	
	@Test
	public void testBasicEqualDefaultRule() {
		assertEquals(basicRule.calculateDaysTariffReturn(10), defaultRule.calculateDaysTariffReturn(10), 0.001);
	}
	
	@Test
	public void testArrayOddRule() {
		assertEquals(2.55, oddArrayRule.calculateDaysTariffReturn(10), 0.001);
	}
	
	@Test
	public void testArrayEvenRule() {
		assertEquals(2.55, evenArrayRule.calculateDaysTariffReturn(10), 0.001);
	}
	
	@Test
	public void testOddListRule() {
		assertEquals(2.55, oddListRule.calculateDaysTariffReturn(10), 0.001);
	}
	
	@Test
	public void testEvenListRule() {
		assertEquals(2.55, evenListRule.calculateDaysTariffReturn(10), 0.001);
	}
	
	@Test(expected=Exception.class)
	public void testExceptionNegativeKwH() {
		evenListRule.calculateDaysTariffReturn(-10);
	}
	
	@Test
	public void failure() {
		System.out.println(evenListRule.toString());
		
		fail("Test hasn't been written properly, lol");
	}

}
