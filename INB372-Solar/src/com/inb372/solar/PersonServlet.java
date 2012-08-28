package com.inb372.solar;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.*;

@SuppressWarnings("serial")
public class PersonServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(PersonServlet.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));

		Person ian = new Person("Ian", 33);
		Person nicole = new Person("Nicole", 29);
		Person submitted = new Person(name, age);
		
		// Javascript object notation
		JSONObject person1 = new JSONObject();
		JSONObject person2 = new JSONObject();
		JSONObject person3 = new JSONObject();
		
		JSONArray persons = new JSONArray();
		
		JSONObject json = new JSONObject();
		
		try {
			person1.put("name", ian.getName());
			person1.put("age", ian.getAge());
			person2.put("name", nicole.getName());
			person2.put("age", nicole.getAge());
			person3.put("name", submitted.getName());
			person3.put("age", submitted.getAge());
			
			persons.put(person1);
			persons.put(person2);
			persons.put(person3);
			
			json.put("Persons", persons);
		}
		catch (JSONException e) {
			
		}
		
		response.setContentType("application/json");
		response.getWriter().write(json.toString());
		
		log.log(Level.WARNING, json.toString());
	}
}
