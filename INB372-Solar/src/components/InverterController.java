package components;

import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class InverterController {
	
	private DataProvider data = new DataProvider();
	
	public JSONObject getInverters() {
		return data.getInverters();
	}
	
	public JSONObject getInverter(String model) {
		return data.getInverter(model);
	}
	
	public JSONObject getInverterManufacturers() {
		return data.getInverterManufacturers();
	}
	
	public JSONObject getInverterModels(String manufacturer) {
		return data.getInverterModels(manufacturer);
	}
	
	public JSONObject getInverterEfficiency(String efficiency) {
		return data.getInverterEfficiency(efficiency);
	}
	
	public JSONObject addInverter(Inverter inverter) {
		return data.addInverter(inverter);
	}
	
	public JSONObject deleteInverter(String model) {
		return data.deleteInverter(model);
	}

}
