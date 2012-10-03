package solar;

public class TariffRule {

String State;
String Description;
TariffRuleSet TRS;
Boolean Gross;

	public TrariffRule(String State, Boolean Gross, double TarriffRate) {
		this.State = State;
		this.Description =Description;
		this.Gross = Gross;
		this.TRS = new TRS (TarriffRate);
	}
	
}
