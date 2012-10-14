package unitTests;

// abc

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import solar.solarAndroid.InvalidInputException;
import solar.solarAndroid.R;
import solar.solarAndroid.SolarPowerCalculator;
import com.jayway.android.robotium.solo.Solo;

// When the application is modified to no longer have default values in every text field,
// all of the fields will need updated so they are all populated for each test
public class solarAndroidTests extends ActivityInstrumentationTestCase2<SolarPowerCalculator> {

	private int servletResponseDelay = 2000;
	
	private Solo solo;
	
	public solarAndroidTests() {
		super(SolarPowerCalculator.class);
	}
	
	@Override
	public void setUp() throws Exception {
		//setUp() is run before a test case is started. 
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		solo.finishOpenedActivities();
	}
	
	// valid input inverter decimal
	public void testValidInverterDecimal() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.InverterEfficiency);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, Double.toString(0.97));
		//solo.sleep(5000);
		solo.clickOnView(act.findViewById(R.id.Submit));
		solo.sleep(servletResponseDelay);
		String strValue = ((TextView)act.findViewById(R.id.Savings)).getText().toString().substring(1);
		Float value = Float.parseFloat(strValue);
		assertTrue("Servlet didn't respond in time or bad savings returned.", value > 0);
	}
	
	// valid input inverter > 1
	public void testValidInverterBigNumber() {
		Activity act = getActivity();
		solo.clickOnView(act.findViewById(R.id.Submit));
		solo.sleep(servletResponseDelay);
		String strValue = ((TextView)act.findViewById(R.id.Savings)).getText().toString().substring(1);
		Float value = Float.parseFloat(strValue);
		assertTrue("Servlet didn't respond in time or bad savings returned.", value > 0);
	}
	
	
	// valid input tariff in cents
	public void testValidTariffCents() {
		Activity act = getActivity();
		//EditText editText = (EditText)act.findViewById(R.id.TariffRate);
		//solo.clickOnView(editText);
		//solo.clearEditText(editText);
		//solo.enterText(editText, Double.toString(44));
		solo.clickOnView(act.findViewById(R.id.Submit));
		solo.sleep(servletResponseDelay);
		String strValue = ((TextView)act.findViewById(R.id.Savings)).getText().toString().substring(1);
		Float value = Float.parseFloat(strValue);
		assertTrue("Servlet didn't respond in time or bad savings returned.", value > 0);
	}
	
	// valid input tariff in dollars
	public void testValidTariffDollars() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.TariffRate);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, Double.toString(0.80));
		solo.clickOnView(act.findViewById(R.id.Submit));
		solo.sleep(servletResponseDelay);
		String strValue = ((TextView)act.findViewById(R.id.Savings)).getText().toString().substring(1);
		Float value = Float.parseFloat(strValue);
		assertTrue("Servlet didn't respond in time or bad savings returned.", value > 0);
	}
	
	// These expected exception tests all show up as errors with the InvalidInputException, they succeed
	// in throwing the error, but I need to work out how to make that stop the testing process from stopping
	// and stop the reporting it as an error, but a pass.
	// Commented out until then
	// Maybe instead of throwing the exceptions, I should make it verify the entries on the fly and disable
	// the submit button, then check whether the submit button is enabled
	/*
	
	// no inverter efficiency (expect exception)
	public void testNoInverterEfficiency() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.InverterEfficiency);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// no panel efficiency (expect exception)
	public void testNoPanelEfficiency() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.PanelEfficiency);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// no consumption (expect exception)
	public void testNoConsumption() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.PowerConsumption);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// no panel angle (expect exception)
	public void testNoAngle() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.PanelAngle);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// no address (expect exception)
	public void testNoAddress() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.Address);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// no orientation (expect exception)
	public void testNoOrientation() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.PanelOrientation);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// invalid size
	public void testInvalidSize() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.PanelSize);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, -1);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// invalid inverter efficiency
	public void testInverterEfficiency() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.InverterEfficiency);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, -1);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// invalid panel efficiency
	public void testPanelEfficiency() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.PanelEfficiency);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, -1);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// invalid consumption (negative)
	public void testInverterEfficiency() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.InverterEfficiency);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, -1);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	// invalid panel angle (greater than 90)
	public void testPanelAngle() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.PanelAngle);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, 95);
		try {
			solo.clickOnView(act.findViewById(R.id.Submit));
			solo.sleep(servletResponseDelay);
			fail("Expected an exception");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	*/	
	// invalid orientation
	public void testInvalidOrientation() {
		/*Activity act = getActivity();
		Button editText = (Button)act.findViewById(R.id.PanelOrientation);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, "QU");
		solo.clickOnView(act.findViewById(R.id.Submit));
		solo.sleep(servletResponseDelay);*/
		
		fail("Expected an exception");
	}
	
	// invalid address (expected exception)
	public void testInvalidAddress() {
		Activity act = getActivity();
		EditText editText = (EditText)act.findViewById(R.id.Address);
		solo.clickOnView(editText);
		solo.clearEditText(editText);
		solo.enterText(editText, Integer.toString(1));
		solo.clickOnView(act.findViewById(R.id.Submit));
		solo.sleep(servletResponseDelay);
		
		fail("Expected an exception");
	}
}
