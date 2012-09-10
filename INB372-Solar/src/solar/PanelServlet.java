package solar;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.*;
import com.google.appengine.labs.repackaged.org.json.*;

public class PanelServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(SolarServlet.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String manufacturer = "";
		String model = "";
		Integer power = 0;
		Integer length = 0;
		Integer width = 0;
		String powerStr = "";
		String lengthStr = "";
		String widthStr = "";
		
		boolean validInput = false;

		try {
			manufacturer = request.getParameter("manufacturer");
			model = request.getParameter("model");
			power = Integer.parseInt(request.getParameter("power"));			
			length = Integer.parseInt(request.getParameter("newPanelLength"));			
			width = Integer.parseInt(request.getParameter("newPanelWidth"));
			
			validInput = true;
		}
		catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		powerStr = power.toString();
		lengthStr = length.toString();
		widthStr = width.toString();
		
		JSONArray jsonArray = new JSONArray();
		JSONObject returnJsonObj = new JSONObject();
		
		if (validInput) {
			
			//Key panelKey = KeyFactory.createKey("Panel", "iDub");
			
			Entity panel = new Entity("Panel", model);
			panel.setProperty("manufacturer", manufacturer);
			panel.setProperty("model", model);
			panel.setProperty("power", powerStr);
			panel.setProperty("length", lengthStr);
			panel.setProperty("width", widthStr);
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	        datastore.put(panel);

	        Query query = new Query("Panel").addSort("manufacturer", Query.SortDirection.ASCENDING);
	        //Query query = new Query("Panel");
	        query.getKind();
	        List<Entity> panels = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	        
	        if (panels.size() > 0) {
		        try {
		        	for (Entity panelInList : panels) {
		        		JSONObject panelObj = new JSONObject();	        	
						panelObj.put("manufacturer", panelInList.getProperty("manufacturer"));
						panelObj.put("model", panelInList.getProperty("model"));
						panelObj.put("power", panelInList.getProperty("power"));
						panelObj.put("length", panelInList.getProperty("length"));
						panelObj.put("width", panelInList.getProperty("width"));
						
						jsonArray.put(panelObj);
		        	}
			        
			        returnJsonObj.put("Panels", jsonArray);
		        }
	        	catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}			
		else {

		}		
		
		response.setContentType("application/json");
		response.getWriter().write(returnJsonObj.toString());
		
		log.log(Level.WARNING, returnJsonObj.toString());
	}

}
