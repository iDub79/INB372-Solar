package solar;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.*;

import components.Inverter;
import components.Panel;

import exceptions.*;


public class SolarServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SolarServlet.class.getName());
	
	private Panel panel;
	private String panelManufacturer = "";
	private String panelModel = "";
	private float panelEfficiency = 0;
	private Integer panelQty = 0;
	
	private Inverter inverter;
	private String inverterManufacturer = "";
	private String inverterModel = "";
	private float inverterEfficiency = 0;
				
	private float angle = 0;			
	private float consumption = 0;
	private String address = "";
	private String latitude = "";
	private String longitude = "";
	private String orientation = "";
	private Integer sunlight = 0;
	private float tariffAmount = 0;
	
	private Calculator calc;
	private TariffCalculation tariff;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String reqOption = request.getParameter("option");		

		float annualSavings = 0;
		boolean validInput = false;
		
		try {
			panelManufacturer = request.getParameter("panelManufacturer");
			panelModel = request.getParameter("panelModel");
			panelEfficiency = Float.parseFloat(request.getParameter("panelEfficiency"));
			panelQty = Integer.parseInt(request.getParameter("panelQty"));
			inverterManufacturer = request.getParameter("inverterManufacturer");
			inverterModel = request.getParameter("inverterModel");
			inverterEfficiency = Float.parseFloat(request.getParameter("inverterEfficiency"));
			
			angle = Float.parseFloat(request.getParameter("angle"));						
			consumption = Float.parseFloat(request.getParameter("consumption"));			
			address = request.getParameter("address");
			latitude = request.getParameter("latitude");
			longitude = request.getParameter("longitude");
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

			try {				
				createPanel();
				createInverter();
				createCalculator();
				createTariff();
				annualSavings = (float) (Math.round(tariff.calAnnualSaving() * 100.0f) / 100.0f);				
			}
			catch (PanelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (InverterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	private void createTariff() throws TariffException, CalculatorException {
		tariff = new TariffCalculation(calc, tariffAmount);
	}

	private void createCalculator() throws CalculatorException {
		calc = new Calculator(panel, inverter, panelQty, consumption, angle);
	}

	private void createInverter() throws InverterException {
		inverter = new Inverter(inverterManufacturer, inverterModel, inverterEfficiency);
	}

	public void createPanel() throws PanelException {
		panel = new Panel(panelManufacturer, panelModel, panelEfficiency);
	}
}
