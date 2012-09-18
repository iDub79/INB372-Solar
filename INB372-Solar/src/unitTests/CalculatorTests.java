package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import exceptions.CalculatorException;
import exceptions.SolarSystemException;

import solar.Calculator;

import static java.lang.Math.*;

public class CalculatorTests {

	@Test (expected = CalculatorException.class)
	public void nullSystemInConstructor() throws CalculatorException {
		Calculator nullSystem = new Calculator(null, null, null, 0, 0);
	}
}