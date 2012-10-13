package solar;

import java.util.ArrayList;

public class TariffRuleList {
	
	private ArrayList<TariffRule> rules = new ArrayList<TariffRule>();	
	
	
	public ArrayList<TariffRule> getrules() {		
		setupTariffrules(); 		
		return rules;
	}
	
	
	public ArrayList<TariffRule> getRuleForState(String state) {		
		setupTariffrules();
 		
		ArrayList<TariffRule> Staterules = new ArrayList<TariffRule>();
		
		for (int i = 0; i < rules.size(); i++) {
			if(rules.get(i).getState().equals(state)) {
				Staterules.add(rules.get(i));
			}
		}
		return Staterules;
	}
	
	
	private void setupTariffrules() {
		rules.add( new TariffRule ("Victoria", "Before July 7 2012", false,  0.08f));
		rules.add( new TariffRule ("Victoria", "Before July 7 2012", false,  0.25f));
		rules.add( new TariffRule ("Victoria", "From September 3 2012", false,  0.08f));
		rules.add( new TariffRule ("South Australia", "27 January 2012 to 30 June 2012", false, 0.71f));
		rules.add( new TariffRule ("South Australia", "1 July 2012 to 30 June 2013", false, 0.98f));
		rules.add( new TariffRule ("South Australia", "1 July 2013 to 30 June 2014", false, 0.112f));
		rules.add( new TariffRule ("Queensland", "From July 10 2012", false, 0.08f));
		rules.add( new TariffRule ("Queensland", "Before July 10 2012", false, 0.44f));
		rules.add( new TariffRule ("Western Australia", "", false, 0.22f));
		rules.add( new TariffRule ("Northern Territory", "Before July 7 2012", true, 0.2777f));
		rules.add( new TariffRule ("Tasmania", "Before July 7 2012", false, 0.22648f));
		rules.add( new TariffRule ("New South Wales", "", true, 0.00f));
		rules.add( new TariffRule ("Australian Capital Territory", "", true, 0.00f)); 
	}
}
