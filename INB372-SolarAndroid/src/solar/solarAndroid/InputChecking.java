package solar.solarAndroid;


public class InputChecking {
	//Panel efficiency is not a percentage but the maximum power output of the panel in watts
		public static Float getPanelEfficiency(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Panel efficiency string was null");
			else if (text.length() == 0) {
				throw new InvalidInputException("Require panel efficiency to be input.");
			}
			
			Float panelEfficiency;
			try {
				panelEfficiency = Float.parseFloat(text); 
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Consumption must be a number");
			}
			
			if (panelEfficiency > 0) {
				return panelEfficiency;
			} else {
				// not sure whether I should return false or throw an exception
				throw new InvalidInputException("Panel efficiency must be positive.");
				//return null;
			}
		}
		
		public static Float getInverterEfficiency(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Inverter efficiency string was null");
			else if (text.length() == 0) {
				throw new InvalidInputException("Require inverter efficiency to be input.");
			}
			
			Float inverterEfficiency;
			try {
				inverterEfficiency = Float.parseFloat(text); 
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Consumption must be a number");
			}
			// inverter efficiency can be input either as a decimal representation of a percentage
			// or an integer
			if (inverterEfficiency >= 50 && inverterEfficiency <= 100) {
				return inverterEfficiency;
			} else if (inverterEfficiency >= 0.5 && inverterEfficiency <= 1.0) {
				return inverterEfficiency * 100;
			} else {
				// not sure whether I should return false or throw an exception
				throw new InvalidInputException("Inverter efficiency must be between 50% and 100%.");
				//return null;
			}
		}
		
		public static Float getTariff(String text) throws InvalidInputException{
			if (text == null)
				throw new InvalidInputException("Tariff string was null");
			else if (text.length() == 0) {
				throw new InvalidInputException("Require tariff rate to be input.");
			}

			Float tariff;
			try {
				tariff = Float.parseFloat(text); 
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Consumption must be a number");
			}			
			
			// Some weird place might have really high feed-in tariff returns
			if (tariff >= 0 && tariff < 1) {	// value must have been input in dollars!
				return tariff;
			}
			if (tariff >= 1 && tariff <= 500) {
				return tariff / 100;
			} else {
				// not sure whether I should return false or throw an exception
				throw new InvalidInputException("Feed-in tariff rate must be between 0 and 500 cents");
				//return null;
			}
		}

		public static Float getConsumption(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Consumption string was null");
			else if (text.length() == 0) {
				throw new InvalidInputException("Require power consumption to be input.");
			}
			
			Float consumption;
			try {
				consumption = Float.parseFloat(text); 
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Consumption must be a number");
			}
			
			
			// Some weird place might have really high feed-in consumption returns
			if (consumption >= 0) {	// value must have been input in dollars!
				return consumption;
			} else {
				// not sure whether I should return false or throw an exception
				throw new InvalidInputException("Consumption must be positive");
				//return null;
			}
		}

		public static Float getAngle(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Angle string was null");
			else if (text.length() == 0) {
				throw new InvalidInputException("Require angle to be input.");
			}
			
			Float angle;
			try {
				angle = Float.parseFloat(text); 
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Panel angle must be a number");
			}
			
			
			if (angle >= 0 && angle <= 90) {	// value must have been input in dollars!
				return angle;
			} else {
				// not sure whether I should return false or throw an exception
				throw new InvalidInputException("Angle must be between 0 and 90");
				//return null;
			}
		}

		// orientation will probably end up being a N, S, E, W combination or a number of degrees between 
		// 1 and 360 and will need to be converted to something consistent
		public static String getOrientation(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Orientation string was null");
			else if (text.length() == 0) {
				throw new InvalidInputException("Require orientation to be input.");
			}
			
			Float panelOrientation;
			try {
				panelOrientation = Float.parseFloat(text); 
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Panel orientation must be a number");
			}
			
			if (panelOrientation < 0 || panelOrientation > 360)
				throw new InvalidInputException("Panel orientation must be between 0 and 360");
			
			if (true) {		// replace with thing that filters the input and converts to consistent thing
				return panelOrientation.toString();
			} else
				throw new InvalidInputException("Orientation is somehow invalid.");		// fix message
		}

		// Later will probably return latitude/longitude as some sort of struct and address will just be one
		// of the ways this information will be obtained
		public static String getAddress(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Address string was null");
			
			if (true) {		// replace with thing that filters the input and converts to consistent thing
				return text;
			} else {
				throw new InvalidInputException("Address is somehow invalid.");		// fix message
			}
		}
		
		public static Integer getPanelQuantity(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Panel quantity string was null");
			else if (text.length() == 0) {
				throw new InvalidInputException("Require quantity to be input.");
			}
			
			Integer panelQuantity;
			try {
				panelQuantity = Integer.parseInt(text); 
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Panel quantity must be a number");
			}
			
			if (panelQuantity > 0) {		// replace with thing that filters the input and converts to consistent thing
				return panelQuantity;
			} else {
				throw new InvalidInputException("Need at least 1 panel");		// fix message
			}
		}
		
		public static String getInverterManufacturer(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Inverter manufacturer string was null");
			return text;
		}
		
		public static String getPanelManufacturer(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Panel manufacturer string was null");
			return text;
		}
		
		public static String getPanelModel(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Panel model string was null");
			return text;
		}
		
		public static String getInverterModel(String text) throws InvalidInputException {
			if (text == null)
				throw new InvalidInputException("Inverter model string was null");
			return text;
		}
		
		
}
