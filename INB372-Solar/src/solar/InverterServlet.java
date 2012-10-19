package solar;

import components.Inverter;
import components.InverterController;
import exceptions.InverterException;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.appengine.labs.repackaged.org.json.*;

public class InverterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(InverterServlet.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String reqOption = request.getParameter("option");
		JSONObject returnJsonObj = new JSONObject();
		
		if (reqOption.equals("addInverter")) {		
			String manufacturer = "";
			String model = "";
			Integer efficiency = 0;
			
			boolean validInput = false;
	
			try {
				manufacturer = request.getParameter("manufacturer");
				model = request.getParameter("model");
				efficiency = Integer.parseInt(request.getParameter("efficiency"));			
				
				validInput = true;
				
				if (validInput) {
					// create new Panel					
					InverterController ctrl = new InverterController();
					Inverter i = new Inverter(manufacturer, model, efficiency);
					returnJsonObj = ctrl.addInverter(i);					
				}			
				else {
					returnJsonObj.put("Success", false);
				}
			}
			catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();		
			}
			catch (InverterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
		else if (reqOption.equals("getInverters")) {
			InverterController ctrl = new InverterController();
			returnJsonObj = ctrl.getInverters();
		}
		else if (reqOption.equals("getInverterManufacturers")) {
			InverterController ctrl = new InverterController();
			returnJsonObj = ctrl.getInverterManufacturers();
		}
		else if (reqOption.equals("getInverterModels")) {
			String manufacturer = request.getParameter("manufacturer");
			InverterController ctrl = new InverterController();
			returnJsonObj = ctrl.getInverterModels(manufacturer);
		}
		else if (reqOption.equals("getInverterEfficiency")) {
			String model = request.getParameter("model");
			InverterController ctrl = new InverterController();
			returnJsonObj = ctrl.getInverterEfficiency(model);
		}
		else if (reqOption.equals("deleteInverter")) {
			String model = request.getParameter("model");
			InverterController ctrl = new InverterController();
			returnJsonObj = ctrl.deleteInverter(model);
		}
		
		response.setContentType("application/json");
		response.getWriter().write(returnJsonObj.toString());
		
		log.log(Level.WARNING, returnJsonObj.toString());
	}
}
