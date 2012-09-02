package solar;

import solar.Calculator;
import solar.TariffException;


public class TariffCalculation {

	//private Calculator solar;
	final double cost = 0.44;
	double electric;
	//
	public TariffCalculation(Calculator solar) throws TariffException {
		if (solar.calcDailyExcess() < 0)
			throw new TariffException ("This value must greater than 0");
		
		this.electric = solar.calcDailyExcess();
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
