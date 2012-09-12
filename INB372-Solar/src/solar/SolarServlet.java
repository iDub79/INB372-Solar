package solar;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.*;


public class SolarServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SolarServlet.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		float panelSize = 0;
		float panelEfficiency = 0;
		float inverterEfficiency = 0;			
		float angle = 0;			
		float consumption = 0;
		String address = "";
		String orientation = "";
		Integer sunlight = 0;
		float tariffAmount = 0;
		
		float annualSavings = 0;
		boolean validInput = false;
		
		Calculator calc;
		TariffCalculation tariff;		
		
		
		try {
			panelSize = Float.parseFloat(request.getParameter("panelSize"));
			panelEfficiency = Float.parseFloat(request.getParameter("panelEfficiency"));
			inverterEfficiency = Float.parseFloat(request.getParameter("inverterEfficiency"));			
			angle = Float.parseFloat(request.getParameter("angle"));			
			consumption = Float.parseFloat(request.getParameter("consumption"));
			address = request.getParameter("address");
			orientation = request.getParameter("orientation");
			sunlight = Integer.parseInt(request.getParameter("sunlight"));
			tariffAmount = Float.parseFloat(request.getParameter("tariff"));
			
			validInput = true;
		}
		catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (validInput) {
			SolarSystemInfo solarInfo;
			try {
				solarInfo = new SolarSystemInfo(panelSize, panelEfficiency, inverterEfficiency, angle, consumption);
				
				try {
					calc = new Calculator(solarInfo);
					tariff = new TariffCalculation(calc, tariffAmount);
					annualSavings = (float) (Math.round(tariff.calAnnualSaving() * 100.0f) / 100.0f);
				}
				catch (CalculatorException calcEx) {
					// TODO Auto-generated catch block
					calcEx.printStackTrace();
				}
				catch (TariffException tarEx) {
					// TODO Auto-generated catch block
					tarEx.printStackTrace();
				}
			}
			catch (SolarSystemException e) {
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
