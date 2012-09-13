package components;

import exceptions.PanelException;

public class Panel {

	private String manufacturer;
	private String model;	
	private float power;
	
	public Panel(String manufacturer, String model, float power) throws PanelException {
		
		if (manufacturer.isEmpty()) {
			throw new PanelException("Panel manufacturer cannot be empty");
		}
		else {
			this.manufacturer = manufacturer;
		}
		if (model.isEmpty()) {
			throw new PanelException("Panel model cannot be empty");
		}
		else {
			this.model = model;
		}
		if (power < 0) {
			throw new PanelException("Panel's power cannot be less than 0.");
		}
		else {
			this.power = power;
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

	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
	}
	
}
