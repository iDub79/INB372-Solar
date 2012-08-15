package com.inb372.solar;

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

		
		
		//log.log(Level.WARNING, json.toString());
	}
}
