package com.inb372.solar;

public class SolarSystemInfo {
	
	private float sizeOfPanels;
	private float wattRating;
	private float inverterEffeciency;
	private float panelAngle;
	private float dayTimePowerConsumption;
	
	SolarSystemInfo(float pSize, float wRating,
			float iEffeciency, float pAngle, float powerConsumption)
			{
		sizeOfPanels = pSize;
		wattRating = wRating;
		inverterEffeciency = iEffeciency;
		panelAngle = pAngle;
		dayTimePowerConsumption = powerConsumption;
			}

	public float getSizeOfPanels() {
		return sizeOfPanels;
	}

	public void setSizeOfPanels(float sizeOfPanels) {
		this.sizeOfPanels = sizeOfPanels;
	}

	public float getWattRating() {
		return wattRating;
	}

	public void setWattRating(float wattRating) {
		this.wattRating = wattRating;
	}

	public float getInverterEffeciency() {
		return inverterEffeciency;
	}

	public void setInverterEffeciency(float inverterEffeciency) {
		this.inverterEffeciency = inverterEffeciency;
	}

	public float getPanelAngle() {
		return panelAngle;
	}

	public void setPanelAngle(float panelAngle) {
		this.panelAngle = panelAngle;
	}

	public float getDayTimePowerConsumption() {
		return dayTimePowerConsumption;
	}

	public void setDayTimePowerConsumption(float dayTimePowerConsumption) {
		this.dayTimePowerConsumption = dayTimePowerConsumption;
	}

}
