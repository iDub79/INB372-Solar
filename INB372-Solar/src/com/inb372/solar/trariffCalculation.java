package com.inb372.solar;



public class trariffCalculation {

	private Calculator solar;
	final double cost=0.44;
	double electric;
	//
	public trariffCalculation(Calculator solar) throws trariffException {
		if (solar.calcDailyExcess() < 0)
		throw new trariffException ("This value must greater than 0");
		this.electric= solar.calcDailyExcess();
	}
	
	//Calculate the Annual Electric
	public double calAnnualElectric() {	
		return electric * 365;
	}
	
	//Calculate the Annual Saving
	public double calAnnualSaving() {
		return calAnnualElectric() * cost;
	}
}
