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
		if (system == null) {
			throw new CalculatorException();
		}
		else {
			this.system = system;
		}
	}
	
	private float calcPanelEff() throws CalculatorException {
		float wKwConversion = 1000;
		
		if (system.getSizeOfPanels() == 0.0f) {
			throw new CalculatorException();
		}	
		else {
			return ((system.getWattRating() / system.getSizeOfPanels()) / wKwConversion);
		}
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
	
	private float calcDailySunHit() {
		float dailySun = 7;
		hourlySun = calcSunPerHour();
		angSunPanel = calcAngleSunPanel(angSunGround);
		hourlyInsolation = calcHourlyInsolation(angSunPanel);
		
		for (int i = 0; i < noHoursInDay; i++) {
			dailySun += hourlyInsolation[i] * hourlySun[i];
		}
		
		return dailySun;
	}
	
	public float calcDailyPower() throws CalculatorException {
		return calcPanelEff() * system.getInverterEffeciency() * calcDailySunHit();
	}
	
	public float calcDailyExcess() throws CalculatorException {
		return calcDailyPower() - system.getDayTimePowerConsumption();
	}
}

