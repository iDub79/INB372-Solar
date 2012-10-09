package solar;

import java.util.ArrayList;

public class TariffRuleList {
	
	static ArrayList<TariffRule> Rules = new ArrayList<TariffRule>();

	
	
	public static ArrayList<TariffRule> getRuleForState (String State) {
//		Rules.add( new TariffRule ("Victoria", "Before July 7 2012",false,0.08f)); 
//		Rules.add( new TariffRule ("Victoria", "",false,0.25f)); 
//		Rules.add( new TariffRule ("Victoria", "",false,0.60f)); 
		Rules.add( new TariffRule ("South Australia", false,0.24f)); 
		Rules.add( new TariffRule ("South Australia", false,0.44f)); 
		Rules.add( new TariffRule ("Victoria", false,0.08f)); 
		Rules.add( new TariffRule ("Victoria", false,0.08f)); 
		Rules.add( new TariffRule ("Victoria", false,0.08f)); 
		Rules.add( new TariffRule ("Victoria", false,0.08f)); 
		Rules.add( new TariffRule ("Victoria", false,0.08f)); 
		
		ArrayList<TariffRule> StateRules = new ArrayList<TariffRule>();
		
		for (int i = 0; i < Rules.size(); i++)
		{
			// I changed this to to stiring, rally what it should do is call a method of TarriffRule that returns the cuty its for. Calin
			if(Rules.get(i).toString() == State)
			StateRules.add(Rules.get(i)); 
		}
		return StateRules;
	}
}
