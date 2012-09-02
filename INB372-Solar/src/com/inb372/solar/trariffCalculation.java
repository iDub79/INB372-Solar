package com.inb372.solar;



public class trariffCalculation {

	private Calculator solar;
	final float cost=0.44;
	float electric;
	//
	public trariffCalculation(Calculator solar) throws trariffException {
		if (solar.calcDailyExcess() < 0)
		throw new trariffException ("This value must greater than 0");
		this.electric= solar.calcDailyExcess();
	}
	
	//Calculate the Annual Electric
	public float calAnnualElectric() {	
		return electric * 365;
	}
	
	//Calculate the Annual Saving
	public float calAnnualSaving() {
		return calAnnualElectric() * cost;
	}
}
