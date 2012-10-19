package unitTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.google.appengine.labs.repackaged.org.json.JSONObject;

import components.DataProvider;

public class DataProviderTests {

	private DataProvider data;
	
	@Before
	@Test
	public void init() {
		data = new DataProvider();
	}
}
