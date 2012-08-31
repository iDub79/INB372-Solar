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

		boolean validInput = false;
		
		try {
			String name = request.getParameter("name");
			Integer panelSize = Integer.parseInt(request.getParameter("panelSize"));
			Integer panelEfficiency = Integer.parseInt(request.getParameter("panelEfficiency"));
			Integer inverterEfficiency = Integer.parseInt(request.getParameter("inverterEfficiency"));
			String address = request.getParameter("address");
			String orientation = request.getParameter("orientation");
			Integer angle = Integer.parseInt(request.getParameter("angle"));
			Integer sunlight = Integer.parseInt(request.getParameter("sunlight"));
			Integer consumption = Integer.parseInt(request.getParameter("consumption"));
			
			validInput = true;
		}
		catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * 1) Instantiate Class based on user input values above
		 * 
		 * 2) Perform calculations
		 * 
		 */
		
		// create a Json object to return amountOfMoneyMade back to the client
		Integer amountOfMoneyMade = 1450;
		JSONObject moneyMade = new JSONObject();
		JSONObject returnJson = new JSONObject();
		
		try {
			if (validInput) {			
				moneyMade.put("Success", true);
				moneyMade.put("Amount", amountOfMoneyMade);
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
