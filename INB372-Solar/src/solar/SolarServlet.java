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
	private float latitude = 0.0f;
	private float longitude = 0.0f;
	private String orientation = "";
	private float tariffAmount = 0;

	private Calculator calc;
	private TariffCalculation tariff;

	private float[][] dailyGen = new float[365][2];
	private float[][] monthlyGen = new float[12][2];
	
	private JSONObject returnJson = new JSONObject();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
			latitude = Float.parseFloat(request.getParameter("latitude"));
			longitude = Float.parseFloat(request.getParameter("longitude"));
			orientation = request.getParameter("orientation");
			tariffAmount = Float.parseFloat(request.getParameter("tariff"));

			validInput = true;
		
			JSONObject moneyMade = new JSONObject();
			
			if (validInput) {
	
				createPanel();
				createInverter();
				createCalculator();
				createTariff();
				annualSavings = (float) (Math.round(tariff.calAnnualSaving() * 100.0f) / 100.0f);
				
				createDailyDisplay();				
				createMonthlyDisplay();
				
				moneyMade.put("Success", true);
				moneyMade.put("Amount", annualSavings);
				moneyMade.put("MonthlyGen", monthlyGen);
				moneyMade.put("DailyGen", dailyGen);
				
				returnJson.put("Savings", moneyMade);
									
			}
			else {
				moneyMade.put("Success", false);
				returnJson.put("Savings", moneyMade);
			}
		}
		catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		catch (JSONException jsonEx) {
			jsonEx.printStackTrace();
		}
		

		response.setContentType("application/json");
		response.getWriter().write(returnJson.toString());

		log.log(Level.WARNING, returnJson.toString());
	}
	

	protected void createDailyDisplay() throws CalculatorException {
		float[] dailyGenerated = calc.makeDailyGenTable();
		float[] dailyExcess = calc.makeDailyExcessTable();
				
		for (int i = 0; i < dailyGen.length; i++) {
			dailyGen[i][0] = dailyGenerated[i];
			dailyGen[i][1] = dailyExcess[i];
		}
	}
	
	protected void createMonthlyDisplay() throws CalculatorException {
		float[] monthlyGenerated = calc.makeMonthlyGenTable();
		float[] monthlyExcess = calc.makeMonthlyExcessTable();
				
		for (int i = 0; i < monthlyGen.length; i++) {
			monthlyGen[i][0] = monthlyGenerated[i];
			monthlyGen[i][1] = monthlyExcess[i];
		}
	}
	

	protected void createTariff() throws TariffException, CalculatorException {
		tariff = new TariffCalculation(calc, tariffAmount);
	}
	

	protected void createCalculator() throws CalculatorException {
		calc = new Calculator(panel, inverter, panelQty, consumption, angle, latitude, longitude);
	}

	
	protected void createInverter() throws InverterException {
		inverter = new Inverter(inverterManufacturer, inverterModel, inverterEfficiency);
	}

	
	protected void createPanel() throws PanelException {
		panel = new Panel(panelManufacturer, panelModel, panelEfficiency);
	}
}