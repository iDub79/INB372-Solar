package solar;

public static class TariffRuleList {
	
	ArrayList<TariffRule> Rules = new ArrayList<TariffRule>();

	Rules.add( new TariffRule ("Victoria", "Before July 7 2012",False,0.08f)); 
	Rules.add( new TariffRule ("Victoria", "",False,0.25f)); 
	Rules.add( new TariffRule ("Victoria", "",False,0.60f)); 
	Rules.add( new TariffRule ("South Australia", False,0.24f)); 
	Rules.add( new TariffRule ("South Australia", False,0.44f)); 
	Rules.add( new TariffRule ("Victoria", False,0.08f)); 
	Rules.add( new TariffRule ("Victoria", False,0.08f)); 
	Rules.add( new TariffRule ("Victoria", False,0.08f)); 
	Rules.add( new TariffRule ("Victoria", False,0.08f)); 
	Rules.add( new TariffRule ("Victoria", False,0.08f)); 
	
	public static ArrayList<TariffRule> getRuleForState (String State) {
		ArrayList<TariffRule> StateRules;
		
		for (int i = 0; i < Rules.size(); i++)
		{
			if(Rules.get(i) == State)
			StateRules.add(Rules[i]); 
		}
		return StateRules;
	}
}
