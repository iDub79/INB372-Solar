package solar;

public class TariffRule {

	private String state;
	private String description;	
	private Boolean gross;
	private double tariffRate;

	public TariffRule(String state, String description, Boolean gross, double tariffRate) {
		this.state = state;
		this.description = description;
		this.gross = gross;
		this.tariffRate = tariffRate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getGross() {
		return gross;
	}

	public void setGross(Boolean gross) {
		this.gross = gross;
	}
	
	public double getTariffRate() {
		return tariffRate;
	}

	public void setTariffRate(double tariffRate) {
		this.tariffRate = tariffRate;
	}
}
