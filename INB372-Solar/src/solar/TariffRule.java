package solar;

public class TariffRule {

String State;
String Description;
TariffRuleSet TRS;
Boolean Gross;

	public TariffRule(String State, Boolean Gross, double TarriffRate) {
		this.State = State;
		this.Description =Description;
		this.Gross = Gross;
		this.TRS = new TariffRuleSet(TarriffRate);
	}
	
}
