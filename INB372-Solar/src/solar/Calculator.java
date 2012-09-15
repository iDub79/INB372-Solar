package solar;
import static java.lang.Math.*;

import components.Inverter;
import components.Panel;

import exceptions.CalculatorException;

public class Calculator {

	private Panel panel;
	private Inverter inverter;
	private float consumption;
	private float panelAngle;
	
	private final int HOURSINDAY = 6;
	private final float AVGDAYSUN = 6.7f;
	
	private float[] angSunGround = new float[HOURSINDAY];
	private float[] angSunPanel = new float[HOURSINDAY];
	private float[] hourlyInsolation = new float[HOURSINDAY];
	private float[] hourlySun = new float[HOURSINDAY];
	
	public Calculator(Panel panel, Inverter inverter, float consumption, float panelAngle) throws CalculatorException {
		if ((panel == null) || (inverter == null)) {
			throw new CalculatorException();
		}
		else {
			this.panel = panel;
			this.inverter = inverter;
			this.consumption = consumption;
			this.panelAngle = panelAngle;
			
			initialiseAngSunGround();
		}
	}

	private void initialiseAngSunGround() {
		angSunGround[0] = 10;
		angSunGround[1] = 35;
		angSunGround[2] = 40;
		angSunGround[3] = 40;
		angSunGround[4] = 35;
		angSunGround[5] = 10;
	}
	
	private float calcPanelEff() throws CalculatorException {
		/*
		float wKwConversion = 1000;
		
		if (system.getSizeOfPanels() == 0.0f) {
			throw new CalculatorException();
		}	
		else {
			return ((system.getWattRating() / system.getSizeOfPanels()) / wKwConversion);
		}
		*/
		return panel.getPower();
	}

	private float[] calcSunPerHour() {		
		float[] hrSun = new float[HOURSINDAY];
		
		for (int i = 0; i < HOURSINDAY; i++) {
			hrSun[i] = AVGDAYSUN / HOURSINDAY;
		}
		
		return hrSun;
	}
	
	private float[] calcAngleSunPanel(float[] angSunGround) {
		float[] angSunPanel = new float[HOURSINDAY];

		for (int i = 0; i < HOURSINDAY; i++) {
			angSunPanel[i] = angSunGround[i] - panelAngle;
		}
		
		return angSunPanel;
	}
	
	private float[] calcHourlyInsolation(float[] angSunPanel) {
		float[] hourlyInsolation = new float[HOURSINDAY];
		
		for (int i = 0; i < HOURSINDAY; i++) {
			hourlyInsolation[i] = (float) sin(angSunPanel[i]);
		}
		
		return hourlyInsolation;
	}
	
	private float calcDailySunHit() {
		float dailySun = 0;
		hourlySun = calcSunPerHour();
		angSunPanel = calcAngleSunPanel(angSunGround);
		hourlyInsolation = calcHourlyInsolation(angSunPanel);
		
		for (int i = 0; i < HOURSINDAY; i++) {
			dailySun += hourlyInsolation[i] * hourlySun[i];
		}
		
		return dailySun;
	}
	
	public float calcDailyPower() throws CalculatorException {
		return calcPanelEff() * inverter.getEfficiency() * calcDailySunHit();
	}
	
	public float calcDailyExcess() throws CalculatorException {
		return calcDailyPower() - consumption;
	}
}

