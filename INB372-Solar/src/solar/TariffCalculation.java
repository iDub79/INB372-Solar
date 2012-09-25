package solar;

import exceptions.CalculatorException;
import exceptions.TariffException;
import solar.Calculator;


public class TariffCalculation {

	float tariffAmount;
	float dailyElectricity;
	
	// Constructor
	public TariffCalculation(Calculator calc, float tariffAmount) throws TariffException, CalculatorException {
		/*
		if (calc.calcDailyExcess() < 0) {
			throw new TariffException ("This value must be greater than 0.");
		}
		else {
			this.dailyElectricity = calc.calcDailyExcess();
			this.tariffAmount = tariffAmount;
		}
		*/
		this.dailyElectricity = calc.calcDailyExcess();
		this.tariffAmount = tariffAmount;
	}
	
	// Calculate the Annual Electric
	public float calAnnualElectric() {	
		return dailyElectricity * 365;
	}
	
	// Calculate the Annual Saving
	public float calAnnualSaving() {
		return calAnnualElectric() * tariffAmount;
	}
}
