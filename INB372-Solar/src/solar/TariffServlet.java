package solar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class TariffServlet extends HttpServlet {
		
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(TariffServlet.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject returnJson = new JSONObject();
		
		TariffRuleList ruleList = new TariffRuleList();
		ArrayList<TariffRule> rules = ruleList.getrules();
		
		try {				
			JSONObject tariffs = new JSONObject();
			
			for (int i = 0; i < rules.size(); i++) {				
				tariffs.put("Success", true);
				tariffs.put("State", rules.get(i).getState());
				tariffs.put("Description", rules.get(i).getDescription());
				tariffs.put("TariffRate", rules.get(i).getTariffRate());
			}
			
			returnJson.put("Tariffs", tariffs);
		}
		catch (JSONException jsonEx) {
			jsonEx.printStackTrace();
		}
		
		response.setContentType("application/json");
		response.getWriter().write(returnJson.toString());
		
		log.log(Level.WARNING, returnJson.toString());
	}
}
