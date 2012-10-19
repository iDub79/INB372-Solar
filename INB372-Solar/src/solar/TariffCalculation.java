package solar;

import exceptions.CalculatorException;
import exceptions.TariffException;
import solar.Calculator;


public class TariffCalculation {

	float tariffAmount;
	float dailyElectricity;
	
	// Constructor
	public TariffCalculation(Calculator calc, float tariffAmount) throws CalculatorException, TariffException {
		this.dailyElectricity = calc.calcDailyExcess();
		this.tariffAmount = tariffAmount;
		
		if (this.tariffAmount < 0) {
			throw new TariffException("Tariff Amount cannot be less than 0");
		}
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
