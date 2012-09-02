package solar;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.*;

@SuppressWarnings("serial")
public class SolarServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(SolarServlet.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		float panelSize = Float.parseFloat(request.getParameter("panelSize"));
		float panelEfficiency = Float.parseFloat(request.getParameter("panelEfficiency"));
		float inverterEfficiency = Float.parseFloat(request.getParameter("inverterEfficiency"));			
		float angle = Float.parseFloat(request.getParameter("angle"));			
		float consumption = Float.parseFloat(request.getParameter("consumption"));
		String address = request.getParameter("address");
		String orientation = request.getParameter("orientation");
		Integer sunlight = Integer.parseInt(request.getParameter("sunlight"));
		double annualSavings = 0;
		
		boolean validInput = false;
		
		try {
			panelSize = Float.parseFloat(request.getParameter("panelSize"));
			panelEfficiency = Float.parseFloat(request.getParameter("panelEfficiency"));
			inverterEfficiency = Float.parseFloat(request.getParameter("inverterEfficiency"));			
			angle = Float.parseFloat(request.getParameter("angle"));			
			consumption = Float.parseFloat(request.getParameter("consumption"));
			address = request.getParameter("address");
			orientation = request.getParameter("orientation");
			sunlight = Integer.parseInt(request.getParameter("sunlight"));
			
			validInput = true;
		}
		catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (validInput) {
			SolarSystemInfo solarInfo = new SolarSystemInfo(panelSize, panelEfficiency, inverterEfficiency, angle, consumption);
			
			Calculator calc = new Calculator(solarInfo);			
			TariffCalculation tariff;
			
			try {
				tariff = new TariffCalculation(calc);
				annualSavings = tariff.calAnnualSaving();
			}
			catch (TariffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JSONObject moneyMade = new JSONObject();
		JSONObject returnJson = new JSONObject();
		
		try {
			if (validInput) {			
				moneyMade.put("Success", true);
				moneyMade.put("Amount", annualSavings);
				returnJson.put("Savings", moneyMade);
			}			
			else {
				moneyMade.put("Success", false);
				returnJson.put("Savings", moneyMade);
			}
		}
		catch (JSONException e) {

		}		
		
		response.setContentType("application/json");
		response.getWriter().write(returnJson.toString());
		
		log.log(Level.WARNING, returnJson.toString());
	}
}
