package solar;

public class TariffRule {

	private String State;
	private String Description;
	//private TariffRuleSet TRS;
	private Boolean Gross;

	public TariffRule(String State, Boolean Gross, double TarriffRate) {
		this.State = State;
		this.Description =Description;
		this.Gross = Gross;
		//this.TRS = new TariffRuleSet(TarriffRate);
	}
	
}
