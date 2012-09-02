package com.inb372.solar;


public class trariffException extends Exception {

	/**
	 * 
	 * Inherited constructor from superclass <code>Exception</code>
	 * 
	 */
	public trariffException() {
		super(); 
	}

	
	/**
	 * 
	 * Class specific constructor 
	 * 
	 * @param msg - specific error message
	 */
	public trariffException(String msg) {
		super("trariffException: " + msg);
	}
}