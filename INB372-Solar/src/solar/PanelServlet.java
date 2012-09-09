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
		int power = 0;
		int length = 0;
		int width = 0;
		
		boolean validInput = false;

		try {
			manufacturer = request.getParameter("manufacturer");
			model = request.getParameter("panelEfficiency");
			power = Integer.parseInt(request.getParameter("power"));			
			length = Integer.parseInt(request.getParameter("newPanelLength"));			
			width = Integer.parseInt(request.getParameter("newPanelWidth"));
			
			validInput = true;
		}
		catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (validInput) {
			
		}

		JSONArray returnJson = new JSONArray();
		
		if (validInput) {
			Key panelKey = KeyFactory.createKey("Panel", manufacturer);
			Entity panel = new Entity("Panel", panelKey);
			panel.setProperty("manufacturer", manufacturer);
			panel.setProperty("model", model);
			panel.setProperty("power", power);
			panel.setProperty("length", length);
			panel.setProperty("width", width);
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	        datastore.put(panel);

	        Query query = new Query("Greeting", panelKey).addSort("manufacturer", Query.SortDirection.ASCENDING);
	        List<Entity> panels = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	        
	        returnJson.put(panels);
		}			
		else {

		}		
		
		response.setContentType("application/json");
		response.getWriter().write(returnJson.toString());
		
		log.log(Level.WARNING, returnJson.toString());
	}

}
