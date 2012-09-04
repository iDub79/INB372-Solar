package solar;
import static java.lang.Math.*;
import solar.CalculatorException;

public class Calculator {

	private SolarSystemInfo system;
	private int noHoursInDay = 6;
	private float[] angSunGround = new float[noHoursInDay];
	private float[] angSunPanel = new float[noHoursInDay];
	private float[] hourlyInsolation = new float[noHoursInDay];
	private float[] hourlySun = new float[noHoursInDay];


	
	public Calculator(SolarSystemInfo system) throws CalculatorException {
		try {
			if (system == null) {
				throw new CalculatorException();
			}
			else {
				this.system = system;
			}
		}
		catch (CalculatorException e) {
			e.printStackTrace();
		}
	}
	
	private float calcPanelEff() {
		float wKwConversion = 1000;
		
		try {
			throw new CalculatorException();
		}
		catch (CalculatorException e) {
			e.printStackTrace();
		}
		
		return ((system.getWattRating() / system.getSizeOfPanels()) / wKwConversion);
	}

	private float[] calcSunPerHour() {
		float avgDaySun = 6.7f;
		float[] hrSun = new float[noHoursInDay];
		
		for (int i = 0; i < noHoursInDay; i++) {
			hrSun[i] = avgDaySun / noHoursInDay;
		}
		
		return hrSun;
	}
	
	private float[] calcAngleSunPanel(float[] angSunGround) {
		float[] angSunPanel = new float[noHoursInDay];
		float angPanelGround = system.getPanelAngle();
		
		for (int i = 0; i < noHoursInDay; i++) {
			angSunPanel[i] = angSunGround[i] - angPanelGround;
		}
		
		return angSunPanel;
	}
	
	private float[] calcHourlyInsolation(float[] angSunPanel) {
		float[] hourlyInsolation = new float[noHoursInDay];
		for (int i = 0; i < noHoursInDay; i++) {
			hourlyInsolation[i] = (float) sin(angSunPanel[i]);
		}
		
		return hourlyInsolation;
	}
	
	private float calcDailySunHit(float[] hourlyInsolation, float[] hourlySun) {
		float dailySun = 0.0f;
		hourlySun = calcSunPerHour();
		angSunPanel = calcAngleSunPanel(angSunGround);
		hourlyInsolation = calcHourlyInsolation(angSunPanel);
		
		for (int i = 0; i < noHoursInDay; i++) {
			dailySun += hourlyInsolation[i] * hourlySun[i];
		}
		
		return dailySun;
	}
	
	public float calcDailyPower() {
		return 7.0f;
	}
	
	public float calcDailyExcess() {
		return calcDailyPower() - system.getDayTimePowerConsumption();
	}
}

