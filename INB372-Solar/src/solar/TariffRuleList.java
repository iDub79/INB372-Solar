package solar;

import java.util.ArrayList;

public class TariffRuleList {
	
	private static ArrayList<TariffRule> Rules = new ArrayList<TariffRule>();	
		
	public static ArrayList<TariffRule> getRuleForState (String state) {
		
		setupTariffRules();
 		
		ArrayList<TariffRule> StateRules = new ArrayList<TariffRule>();
		
		for (int i = 0; i < Rules.size(); i++) {
			if(Rules.get(i).getState().equals(state)) {
				StateRules.add(Rules.get(i));
			}
		}
		return StateRules;
	}
	
	private static void setupTariffRules() {
		Rules.add( new TariffRule ("Victoria", "Before July 7 2012", false, 0.08f)); 
		Rules.add( new TariffRule ("Victoria", "", false, 0.25f)); 
		Rules.add( new TariffRule ("Victoria", "", false, 0.60f)); 
		Rules.add( new TariffRule ("South Australia", "", false, 0.24f)); 
		Rules.add( new TariffRule ("South Australia", "", false, 0.44f));
		Rules.add( new TariffRule ("Queensland", "Before July 10 2012", false, 0.44f)); 
		Rules.add( new TariffRule ("Queensland", "From July 10 2012", false, 0.8f));
	}
}
