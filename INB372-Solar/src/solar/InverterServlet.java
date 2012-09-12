package solar;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.labs.repackaged.org.json.*;

public class InverterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(InverterServlet.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String reqOption = request.getParameter("option");
		
		JSONArray jsonArray = new JSONArray();
		JSONObject returnJsonObj = new JSONObject();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		if (reqOption.equals("addInverter")) {		
			String manufacturer = "";
			String model = "";
			Integer efficiency = 0;
			String efficiencyStr = "";
			
			boolean validInput = false;
	
			try {
				manufacturer = request.getParameter("manufacturer");
				model = request.getParameter("model");
				efficiency = Integer.parseInt(request.getParameter("efficiency"));			
				
				validInput = true;
			}
			catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			efficiencyStr = efficiency.toString();
	
			
			if (validInput) {
				Key inverterKey = KeyFactory.createKey("Inverter", model);
				
				Entity inverter = new Entity("Inverter", inverterKey);
				inverter.setProperty("manufacturer", manufacturer);
				inverter.setProperty("model", model);
				inverter.setProperty("efficiency", efficiencyStr);
				
		        datastore.put(inverter);
	
		        getJSONArray(jsonArray, returnJsonObj, datastore);
			}			
			else {
				
				// TODO ...................
				
			}		
		}
		else if (reqOption.equals("getInverters")) {
			getJSONArray(jsonArray, returnJsonObj, datastore);
		}
		else if (reqOption.equals("deleteInverter")) {
			
			deleteSpec(request, jsonArray, returnJsonObj, datastore, "Inverter");
		}
		
		response.setContentType("application/json");
		response.getWriter().write(returnJsonObj.toString());
		
		log.log(Level.WARNING, returnJsonObj.toString());
	}


	private void deleteSpec(HttpServletRequest request, JSONArray jsonArray, JSONObject returnJsonObj, DatastoreService datastore, String spec) {
		String model = request.getParameter("model");
		
		Query query = new Query(spec);
		Query.Filter filter = new Query.FilterPredicate("model", FilterOperator.EQUAL, model);
		query.setFilter(filter);
		PreparedQuery pq = datastore.prepare(query);
		Entity e = pq.asSingleEntity();
		datastore.delete(e.getKey());
		
		getJSONArray(jsonArray, returnJsonObj, datastore);
	}

	
	private void getJSONArray(JSONArray jsonArray, JSONObject returnJsonObj, DatastoreService datastore) {
		
		Query query = new Query("Inverter").addSort("manufacturer", Query.SortDirection.ASCENDING);
		query.getKind();
		List<Entity> inverters = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

		try {
			if (inverters.size() > 0) {
		   
		    	for (Entity inverterInList : inverters) {
		    		JSONObject inverterObj = new JSONObject();	        	
		    		inverterObj.put("manufacturer", inverterInList.getProperty("manufacturer"));
		    		inverterObj.put("model", inverterInList.getProperty("model"));
		    		inverterObj.put("efficiency", inverterInList.getProperty("efficiency") + "%");
					
					jsonArray.put(inverterObj);
		    	}
		        
		        returnJsonObj.put("Inverters", jsonArray);
		        returnJsonObj.put("Success", true);
			}
			else {
				returnJsonObj.put("Success", "empty");
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
