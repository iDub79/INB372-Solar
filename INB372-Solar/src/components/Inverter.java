package components;

import exceptions.InverterException;

public class Inverter {

	private String manufacturer;
	private String model;	
	private float efficiency;
	
	public Inverter(String manufacturer, String model, float efficiency) throws InverterException {
		
		if (manufacturer.isEmpty()) {
			throw new InverterException("Inverter manufacturer cannot be empty");
		}
		else {
			this.manufacturer = manufacturer;
		}
		if (model.isEmpty()) {
			throw new InverterException("Inverter model cannot be empty");
		}
		else {
			this.model = model;
		}
		if (efficiency < 0) {
			throw new InverterException("Inverter's efficiency cannot be less than 0.");
		}
		else {
			this.efficiency = efficiency;
		}
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public float getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}
}
