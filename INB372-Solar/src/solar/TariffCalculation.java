package solar;

import solar.Calculator;
import solar.TariffException;


public class TariffCalculation {

	float tariffAmount;
	float annualElectricity;
	
	// Constructor
	public TariffCalculation(Calculator calc, float tariffAmount) throws TariffException, CalculatorException {
		if (calc.calcDailyExcess() < 0) {
			throw new TariffException ("This value must be greater than 0.");
		}
		else {
			this.annualElectricity = calc.calcDailyExcess();
			this.tariffAmount = tariffAmount;
		}
	}
	
	// Calculate the Annual Electric
	public float calAnnualElectric() {	
		return annualElectricity * 365;
	}
	
	// Calculate the Annual Saving
	public float calAnnualSaving() {
		return calAnnualElectric() * tariffAmount;
	}
}
