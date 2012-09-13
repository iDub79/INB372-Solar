package components;

import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class PanelController {

	DataProvider data = new DataProvider();
	
	public JSONObject getPanels() {
		return data.getPanels();
	}
	
	public JSONObject getPanel(String model) {
		return data.getPanel(model);
	}
	
	public JSONObject getPanelManufacturers() {
		return data.getPanelManufacturers();
	}
	
	public JSONObject getPanelModels(String manufacturer) {
		return data.getPanelModels(manufacturer);
	}
	
	public JSONObject getPanelPower(String power) {
		return data.getPanelPower(power);
	}
	
	public JSONObject addPanel(Panel panel) {
		return data.addPanel(panel);
	}
	
	public JSONObject deletePanel(String model) {
		return data.deletePanel(model);
	}
}
