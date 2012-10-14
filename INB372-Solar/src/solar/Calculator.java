package solar;

import static java.lang.Math.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import components.Inverter;
import components.Panel;

import solar.SunPosition;
import solar.DistanceCalc;

import exceptions.CalculatorException;

public class Calculator {

	private Panel panel;
	private Inverter inverter;
	private float consumption;
	private float panelAngle;
	private Integer panelQty;
	private float latitude;
	private float longitude;

	private final int DAYSINYEAR = 365;
	private final int HOURSINDAY = 24;
	private final int DAWNTIME = 0;
	private final int SUNHOURS = 1;
	private final int MONTHSINYEAR = 12;
	
	private double[] sunPerDay;
	private int[][] numSunlitHours = new int[2][DAYSINYEAR];

	public Calculator(Panel panel, Inverter inverter, int panelQty, float consumption, float panelAngle, float lat, float lon) throws CalculatorException {
		
		if ((panel == null) || (inverter == null)) {
			throw new CalculatorException();
		}
		else {
			this.panel = panel;
			this.inverter = inverter;
			this.consumption = consumption;
			this.panelAngle = panelAngle;
			this.panelQty = panelQty;
			this.latitude = lat;
			this.longitude = lon;
			
			calcNumSunHours();			
			DistanceCalc distCalc = new DistanceCalc();
			sunPerDay = distCalc.findClosestStation(latitude, longitude);
		}
	}

	private float calcPanelEff() throws CalculatorException {
		return panel.getPower() / 1000;
	}

	private void calcNumSunHours() {
		Calendar calendar = new GregorianCalendar(2012, 0, 1, 0, 0);
		SunPosition sunsElevations = new SunPosition();

		boolean foundDawn = false;
		
		for (int i = 0; i < DAYSINYEAR; i++) {
			for (int j = 0; j < HOURSINDAY; j++) {
				SunPosition.sunPosition(calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH),
						calendar.get(Calendar.HOUR_OF_DAY), 0, 0, 27.4667,
						153.0333);
				
				if (sunsElevations.getElevation() < 0) {
					if (!foundDawn) {
						numSunlitHours[DAWNTIME][i] = j;
						foundDawn = true;
					}
					numSunlitHours[SUNHOURS][i] += 1;
				}
				calendar.add(Calendar.HOUR_OF_DAY, 1);
			}
			foundDawn = false;
		}
	}

	private float[][] calcAngSunGround() {
		float[][] angSunGround = new float[DAYSINYEAR][HOURSINDAY];

		Calendar calendar = new GregorianCalendar(2012, 0, 1, 0, 0);
		SunPosition sunsElevations = new SunPosition();

		for (int i = 0; i < DAYSINYEAR; i++) {
			for (int j = numSunlitHours[DAWNTIME][i]; j < numSunlitHours[DAWNTIME][i] + numSunlitHours[SUNHOURS][i]; j++) {
				SunPosition.sunPosition(calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), j, 0, 0, 27.4667,
						153.0333);
				angSunGround[i][j] = (float) (sunsElevations.getElevation() * -1);
				//System.out.println("Sun angle Day " + i + " hour " + j + " angle: " + angSunGround[i][j]);
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		return angSunGround;
	}

	private float[][] calcAngleSunPanel(float[][] angSunGround) {
		float[][] angSunPanel = new float[DAYSINYEAR][HOURSINDAY];
		float prevAngle = 0.0f;
		float curAngle = 0.0f;
		
		for (int i = 0; i < DAYSINYEAR; i++) {
			for (int j = numSunlitHours[DAWNTIME][i]; j < numSunlitHours[DAWNTIME][i]
					+ numSunlitHours[SUNHOURS][i]; j++) {
				curAngle = angSunGround[i][j];
				if (curAngle < prevAngle) {
					prevAngle = curAngle;
					curAngle = 90 + (90 - curAngle);
				}
				else {
					prevAngle = curAngle;
				}

				if (curAngle < 90 + (90 - panelAngle)) {
					angSunPanel[i][j] = panelAngle + curAngle;
				}
				else {
					angSunPanel[i][j] = 0;
				}
				
				//System.out.println("Panel tp sun angle Day " + i + " hour " + j + " angle: " + angSunPanel[i][j]);
			}

		}
		return angSunPanel;
	}

	private float[][] calcHourlyInsolation(float[][] angSunPanel) {
		float[][] hourlyInsolation = new float[DAYSINYEAR][HOURSINDAY];

		for (int i = 0; i < DAYSINYEAR; i++) {
			for (int j = numSunlitHours[DAWNTIME][i]; j < numSunlitHours[DAWNTIME][i] + numSunlitHours[SUNHOURS][i]; j++) {
				hourlyInsolation[i][j] = (float) sin(toRadians(angSunPanel[i][j]));
				
				//System.out.println("Insol Day " + i + " hour " + j + " insol: " + hourlyInsolation[i][j]);
			}
		}

		return hourlyInsolation;
	}

	private float[][] calcSunPerHour() {
		float[][] hrSun = new float[DAYSINYEAR][HOURSINDAY];

		for (int i = 0; i < DAYSINYEAR; i++) {
			for (int j = numSunlitHours[DAWNTIME][i]; j < numSunlitHours[DAWNTIME][i] + numSunlitHours[SUNHOURS][i]; j++) {
				//System.out.println(sunPerDay[i]);
				//System.out.println(sunPerDay[numSunlitHours[SUNHOURS][i]]);
				hrSun[i][j] = (float) (sunPerDay[i] / numSunlitHours[SUNHOURS][i]);
				
				//System.out.println("Day " + i + " hour " + j + " sunPresent: " + hrSun[i][j]);
			}
		}

		return hrSun;
	}

	public float[] makeDailyGenTable() throws CalculatorException {
		float[] dailyGen = new float[DAYSINYEAR];
		float[][] angSunGround = calcAngSunGround();
		float[][] angSunPanel = calcAngleSunPanel(angSunGround);
		float[][] hourlyInsolation = calcHourlyInsolation(angSunPanel);
		float[][] sunPerHour = calcSunPerHour();
		float panelEff = calcPanelEff();
		float inverterEff = inverter.getEfficiency();

		float oneDayPower;
		for (int i = 0; i < DAYSINYEAR; i++) {
			oneDayPower = 0.0f;
			for (int j = numSunlitHours[DAWNTIME][i]; j < numSunlitHours[DAWNTIME][i] + numSunlitHours[SUNHOURS][i]; j++) {
				oneDayPower += hourlyInsolation[i][j] * sunPerHour[i][j] * panelEff * inverterEff / 100 * panelQty;
			}
			dailyGen[i] = oneDayPower;
		}

		return dailyGen;
	}

	public float[] makeDailyExcessTable() throws CalculatorException {
		float[] dailyGen = makeDailyGenTable();
		float[] dailyExcess = new float[DAYSINYEAR];
		
		for (int i = 0; i < DAYSINYEAR; i++) {
			dailyExcess[i] = dailyGen[i] - consumption;
		}

		return dailyExcess;
	}
	
	public float[] makeMonthlyGenTable() throws CalculatorException {
		float[] dailyGen = makeDailyGenTable();
		float[] monthlyGen = new float[MONTHSINYEAR];
		Calendar calendar = new GregorianCalendar(2012, Calendar.JANUARY, 1, 0, 0);
		//int currMonth = 0;
		int firstDay;
		int dayLimit;
		float monthTotal;
		
		for (int i = 0; i < MONTHSINYEAR; i ++) {			
			
			monthTotal = 0;
			firstDay = calendar.get(Calendar.DAY_OF_YEAR);
			dayLimit = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			// This is a hack fix until I implement dynamic dates throughout the calculator class
			if (i == 11) {
				dayLimit = 29;
			}
			//System.out.println(firstDay);
			//System.out.println(dayLimit);
			
			
			for (int j = firstDay; j< firstDay + dayLimit; j++) {				
				monthTotal += dailyGen[j - 1];				
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
			//System.out.println("Month " + currMonth + " has " + monthTotal + " genned power");
			monthlyGen[i] = monthTotal;			
			//currMonth++;			
		}
		/*
		for (int month = 0; month<12; month++) {
			System.out.println(monthlyGen[month]);
		}
		*/
		return monthlyGen;
		
	}
	
	public float[] makeMonthlyExcessTable() throws CalculatorException {
		float[] monthlyGen = makeMonthlyGenTable();
		float[] monthlyExcess = new float[MONTHSINYEAR];
		
		for (int eachMonth = 0; eachMonth<MONTHSINYEAR;eachMonth++) {
			monthlyExcess[eachMonth] = monthlyGen[eachMonth] - consumption*30;
		}

		return monthlyExcess;
	}

	public float calcDailyPower() throws CalculatorException {
		float[] dailyGen = makeDailyGenTable();
		float avgPowerGen;
		float totalPowerGen = 0;
		for (int i = 0; i < DAYSINYEAR; i++) {
			totalPowerGen += dailyGen[i];
		}
		avgPowerGen = totalPowerGen / DAYSINYEAR;
		//System.out.println("Average Daily Genned Power is: " + avgPowerGen);
		return avgPowerGen;
	}

	public float calcDailyExcess() throws CalculatorException {
		float dailyExcess = calcDailyPower() - consumption;
		//System.out.println("Average Daily Excess Power is: " + dailyExcess);
		return dailyExcess;
	}
	
	@SuppressWarnings("unused")
	private float[] initBrisbaneSunPerDay() {
		float[] brisSun = { 3.3f, 8.3f, 6.1f, 7.1f, 5.8f, 2.4f, 3.6f,
				4.2f, 3.3f, 2.8f, 0.5f, 7.6f, 6.8f, 6.4f, 5.4f, 7.2f, 8.9f, 7.9f,
				5.8f, 6.4f, 6.8f, 8.5f, 8.5f, 8.9f, 8.8f, 8.7f, 8.6f, 7.5f, 7.6f,
				5.2f, 7.8f, 6.8f, 7.1f, 6.8f, 8.4f, 8.1f, 7f, 5.1f, 2.6f, 6.1f,
				6.5f, 4.8f, 8.1f, 7.2f, 7.8f, 5.3f, 4.8f, 5.2f, 6.4f, 7f, 8.1f,
				6.5f, 5.7f, 5.9f, 6.4f, 7.3f, 7.4f, 7.3f, 7.9f, 7.9f, 7.4f, 6.8f,
				2.3f, 3.8f, 6.6f, 6.3f, 5.4f, 5.5f, 4.7f, 6.3f, 5.6f, 5.7f, 6.7f,
				6.3f, 6.2f, 5.2f, 3.6f, 1.7f, 3.5f, 5.2f, 6.8f, 6.9f, 7.1f, 7f, 6f,
				5.3f, 4.9f, 4.1f, 2.3f, 4.6f, 3.9f, 6.2f, 3.8f, 5.7f, 4.8f, 5.9f,
				4.9f, 3f, 5.4f, 5.9f, 4.8f, 6.1f, 6.1f, 6.1f, 5.9f, 2.6f, 4.1f,
				2.1f, 3.3f, 4.3f, 5.3f, 5.4f, 5.1f, 4.6f, 3.3f, 4.6f, 3.6f, 4f,
				4.1f, 4.4f, 5.4f, 5.3f, 3.4f, 5.1f, 4.7f, 5.1f, 5.2f, 4.1f, 1.9f,
				5f, 5.1f, 5f, 5f, 5f, 4.9f, 4.1f, 4.6f, 4.1f, 3.4f, 4.1f, 3.7f,
				3.9f, 2.7f, 4.3f, 4.6f, 4.6f, 4.6f, 4.1f, 3.7f, 4.3f, 4.2f, 3.3f,
				4.1f, 4.3f, 4.4f, 3.3f, 4.3f, 2.2f, 2.7f, 1.7f, 4.3f, 1.8f, 2.6f,
				4.1f, 4f, 3.5f, 4.1f, 4.2f, 4.3f, 4.3f, 4.3f, 4.2f, 4.3f, 4.3f, 4f,
				4f, 3.5f, 2.9f, 3.7f, 2.9f, 3.7f, 3.9f, 3.7f, 3.2f, 4.1f, 4.2f,
				4.3f, 4.3f, 4.3f, 4.4f, 4.5f, 4.4f, 4.4f, 4.3f, 4.2f, 2.5f, 2.4f,
				3.8f, 4.4f, 4.6f, 2.8f, 4.5f, 4.4f, 4.5f, 4.6f, 4.4f, 4.3f, 4.5f,
				4.2f, 3.8f, 3.7f, 4.1f, 4.5f, 4.5f, 4.1f, 4.1f, 3.2f, 4.1f, 3.9f,
				4.5f, 4.7f, 4.8f, 3.8f, 4.9f, 4f, 3.6f, 4.6f, 4.5f, 2.8f, 3.2f,
				5.2f, 4.4f, 3.2f, 3.8f, 3.1f, 2.9f, 3.2f, 2.6f, 1.8f, 5f, 5.4f,
				2.4f, 5.6f, 5.5f, 5f, 5.1f, 4f, 4.8f, 4.5f, 5.7f, 5.9f, 1.7f, 6.4f,
				6.5f, 4.9f, 5f, 6.5f, 6.6f, 6.6f, 6.6f, 6.5f, 6.5f, 6.7f, 6f, 6.3f,
				6.7f, 6.2f, 6.2f, 4.3f, 5.6f, 4.6f, 2.4f, 7.4f, 6.6f, 7.2f, 3.9f,
				7.5f, 6f, 2f, 5.2f, 3.1f, 7.3f, 3.7f, 7.3f, 6.8f, 5f, 5.8f, 5.8f,
				7.7f, 2.3f, 5.7f, 5.3f, 5.3f, 5.8f, 5.3f, 6.3f, 7.5f, 7.9f, 3.8f,
				4.2f, 2.9f, 4.6f, 5.5f, 2.8f, 6.5f, 7f, 8.1f, 7.6f, 6.1f, 8.3f,
				8.5f, 8.7f, 8.7f, 8.6f, 8f, 8.5f, 8.3f, 8.6f, 8.8f, 8.8f, 7.5f,
				7.4f, 8.8f, 8.7f, 8.9f, 8f, 6.7f, 4.1f, 7.4f, 6.5f, 8.3f, 8.8f,
				8.8f, 7.2f, 4.6f, 5.9f, 8f, 7.4f, 5.7f, 4.9f, 3.3f, 6.1f, 7.8f,
				5.1f, 6.4f, 8.1f, 8.8f, 8f, 5.2f, 5.4f, 8.1f, 6.1f, 7.7f, 8.7f,
				6.8f, 8f, 5.6f, 6.2f, 8.3f, 9.3f, 3.7f, 6.3f, 5.2f, 6.3f, 4.8f, };
		
		return brisSun;
	}
}