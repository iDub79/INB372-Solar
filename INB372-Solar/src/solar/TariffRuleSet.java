package solar;


// are there rule sets that are calculated on monthly brackets instead of daily?
public class TariffRuleSet {

	double rate[];
	double rateAppliesAfter[];
	
	// some default rule set, should this be 0.44
	public TariffRuleSet() {
		this(0.44);
	}
	
	public TariffRuleSet(double rate[], double rateAppliesAfter[]) {
		this.rate = rate;
		if (rateAppliesAfter.length % 2 != 0) {
			this.rateAppliesAfter = new double[rateAppliesAfter.length + 1];
			this.rateAppliesAfter[0] = 0;
			for (int i = 0; i < rateAppliesAfter.length; i++)
				this.rateAppliesAfter[i + 1] = rateAppliesAfter[i];
		}
		else {
			this.rateAppliesAfter = rateAppliesAfter;
		}
	}
	
	/* if odd number of elements then either the consumption has already been subtracted or
	 * the system is gross.
	 * if even number of elements then either the system is net (or first appliesAfter value 
	 * is 0) or for some other reason the first bit doesn't apply immediately. 
	 */
	// need to check whether this constructor catches no args
	public TariffRuleSet(double... appliesAfterAndRate) {	// this is variable args, runs like an array
		int count = appliesAfterAndRate.length;
		
		
		if (count % 2 == 0)	{	// even
			// if even, then the first argument is the appliesAfter
			rateAppliesAfter = new double[count / 2];
			rate = new double[rateAppliesAfter.length];
			for (int i = 0; i < rateAppliesAfter.length; i++) {
				rate[i] = appliesAfterAndRate[i * 2 + 1];
				rateAppliesAfter[i] = appliesAfterAndRate[i * 2];
			}
		}
		else {
			// if odd, then the first argument is the rate
			rateAppliesAfter = new double[count / 2 + 1];
			rate = new double[count / 2 + 1];
			
			for (int i = 0; i < rate.length; i++) {
				rate[i] = appliesAfterAndRate[i * 2];
			}
			
			rateAppliesAfter[0] = 0;
			for (int i = 0; i < rateAppliesAfter.length - 1; i++) {
				rateAppliesAfter[i + 1] = appliesAfterAndRate[i * 2 + 1];
			}
		}
	}

	/* calculates how much feed-in tariff returns a person would get on a given day
	 * given the kwh produced in that day.
	 * Accounts for thresholds where the rate changes.
	 */
	public double calculateDaysTariffReturn(double kwh) {
		double kwhToProcess = kwh;
		double profit = 0;
		
		for (int i = rate.length - 1; i >= 0; i--) {
			double difference = kwhToProcess - rateAppliesAfter[i];
			if (difference > 0) {
				profit += (difference * rate[i]);
				kwhToProcess -= difference;
			}
		}
		return profit;
	}
	
	/* Probably be a better way to write this string */
	public String toString() {
		String rule = "";
		for (int i = 0; i < rate.length; i++) {
			Integer rateForCurrentStep = (int)(rate[i] * 100.0);
			rule += rateForCurrentStep.toString() + "c/kWh";
			if (i != rate.length - 1) {
				Double after = rateAppliesAfter[i + 1];
				rule += " to " + after.toString() + "kWh";
			}
			rule += "\n";
		}
		rule += "\n";
		return rule;
	}
}
