package solar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class TariffServlet extends HttpServlet {
		
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(TariffServlet.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String option = request.getParameter("option");		
		
		JSONArray jsonArray = new JSONArray();
		JSONObject returnJson = new JSONObject();
		
		TariffRuleList ruleList = new TariffRuleList();
		ArrayList<TariffRule> rules = new ArrayList<TariffRule>();
		
		if (option.equals("all")) {
			rules = ruleList.getrules();
		}
		else if (option.equals("state")){
			String state = request.getParameter("state");
			rules = ruleList.getRuleForState(state);
		}
		
		
		try {				
			if (rules.size() > 0) {
				for (TariffRule rule : rules) {
					JSONObject tariff = new JSONObject();
					tariff.put("State", rule.getState());
					tariff.put("Description", rule.getDescription());
					tariff.put("TariffRate", rule.getTariffRate());
					
					jsonArray.put(tariff);
				}
				
				returnJson.put("Success", true);
				returnJson.put("Tariffs", jsonArray);
			}
			else {
				returnJson.put("Success", "empty");
			}
		}
		catch (JSONException jsonEx) {
			jsonEx.printStackTrace();
		}
		
		response.setContentType("application/json");
		response.getWriter().write(returnJson.toString());
		
		log.log(Level.WARNING, returnJson.toString());
	}
}
