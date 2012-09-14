package solar;

import exceptions.SolarSystemException;

public class SolarSystemInfo {
	
	private float sizeOfPanels;
	private float wattRating;
	private float inverterEffeciency;
	private float panelAngle;
	private float dayTimePowerConsumption;
	
	public SolarSystemInfo(float pSize, float wRating, float iEffeciency, float pAngle, float powerConsumption) throws SolarSystemException {
		if (pSize < 0) {
			throw new SolarSystemException("Panel size must be positive.");
		}
		else {
			sizeOfPanels = pSize;
		}
		if (wRating < 0) {
			throw new SolarSystemException("Panels efficiency must be positive.");
		}
		else {
			wattRating = wRating;
		}
		if (iEffeciency < 0) {
			throw new SolarSystemException("Panel efficiency must be positive.");
		}
		else {
			inverterEffeciency = iEffeciency;
		}
		if (pAngle < 0) {
			throw new SolarSystemException("Panel angle must be positive.");
		}
		else {
			panelAngle = pAngle;
		}
		if (powerConsumption < 0) {
			throw new SolarSystemException("Panel consumption must be positive.");
		}
		else {
			dayTimePowerConsumption = powerConsumption;
		}
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
