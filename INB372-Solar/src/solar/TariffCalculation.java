package solar;

import solar.Calculator;
import solar.TariffException;


public class TariffCalculation {

	//private Calculator solar;
	final float cost = 0.44f;
	float electric;
	
	// Constructor
	public TariffCalculation(Calculator calc) throws TariffException, CalculatorException {
		if (calc.calcDailyExcess() < 0) {
			throw new TariffException ("This value must greater than 0");
		}
		else {
			this.electric = calc.calcDailyExcess();
		}
	}
	
	// Calculate the Annual Electric
	public float calAnnualElectric() {	
		return electric * 365;
	}
	
	// Calculate the Annual Saving
	public float calAnnualSaving() {
		return calAnnualElectric() * cost;
	}
}
