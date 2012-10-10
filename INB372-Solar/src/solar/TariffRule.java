package solar;

public class TariffRule {

	private String State;
	private String Description;	
	private Boolean Gross;
	//private TariffRuleSet TRS;

	public TariffRule(String State, String Description, Boolean Gross, double TarriffRate) {
		this.State = State;
		this.Description = Description;
		this.Gross = Gross;
		//this.TRS = new TariffRuleSet(TarriffRate);
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Boolean getGross() {
		return Gross;
	}

	public void setGross(Boolean gross) {
		Gross = gross;
	}	
}
