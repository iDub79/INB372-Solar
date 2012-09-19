package solar;

public class SunPosition {

	public static double elevation;
	public static double azimuth;
	/*
	/**
	 * @param args
	 */
	/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//sunPosition(2012, 8, 20, 12, 0, 0, -27.4667, 153.0333);
	}
*/
	public static int cumsum(int month) {
		int[] monthDays = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30 };
		int sum = 0;
		for (int i = 0; i < month; i++) {
			sum += monthDays[i];

		}
		return sum;
	}

	public static void sunPosition(int year, int month, int day) {
		sunPosition(year, month, day, 12, 0, 0, 46.5, 6.5);
	}

	public static void sunPosition(int year, int month, int day, int hour, int min, int sec, double latitude, double longitude) {
		double twopi = 2 * Math.PI;
		double deg2rad = Math.PI / 180;

		// Get day of the year, e.g. Feb 1 = 32, Mar 1 = 61 on leap years
		day = day + cumsum(month);
		boolean leapdays = year % 4 == 0
				&& (year % 400 == 0 || year % 100 != 0) && day >= 60;
		if (leapdays)
			day += 1;

		// Get Julian date - 2400000
		hour = hour + min / 60 + sec / 3600; // hour plus fraction
		int delta = year - 1949;
		int leap = delta / 4; // former leapyears //trunc removed, probably
								// wouldn't do anything since it's become an int
		double jd = 32916.5 + delta * 365 + leap + day + hour / 24;

		// The input to the Atronomer's almanach is the difference between
		// the Julian date and JD 2451545.0 (noon, 1 January 2000)
		double time = jd - 51545;

		// Ecliptic coordinates

		// Mean longitude
		double mnlong = 280.460 + .9856474 * time;
		mnlong = mnlong % 360;
		if (mnlong < 0)
			mnlong = mnlong + 360;

		// Mean anomaly
		double mnanom = 357.528 + .9856003 * time;
		mnanom = mnanom % 360;
		// mnanom[mnanom < 0] <- mnanom[mnanom < 0] + 360
		if (mnanom < 0)
			mnanom = mnanom + 360;
		mnanom = mnanom * deg2rad;

		// Ecliptic longitude and obliquity of ecliptic
		double eclong = mnlong + 1.915 * Math.sin(mnanom) + 0.020
				* Math.sin(2 * mnanom);
		eclong = eclong % 360;
		// eclong[eclong < 0] <- eclong[eclong < 0] + 360
		if (eclong < 0)
			eclong = eclong + 360;
		double oblqec = 23.429 - 0.0000004 * time;
		eclong = eclong * deg2rad;
		oblqec = oblqec * deg2rad;

		// Celestial coordinates
		// Right ascension and declination
		double num = Math.cos(oblqec) * Math.sin(eclong);
		double den = Math.cos(eclong);
		double ra = Math.atan(num / den);
		// ra[den < 0] <- ra[den < 0] + pi
		if (den < 0) // I think
			ra += Math.PI;
		// ra[den >= 0 & num < 0] <- ra[den >= 0 & num < 0] + twopi
		if (den >= 0 && num < 0)
			ra += twopi;
		double dec = Math.asin(Math.sin(oblqec) * Math.sin(eclong));

		// Local coordinates
		// Greenwich mean sidereal time
		double gmst = 6.697375 + .0657098242 * time + hour;
		gmst = gmst % 24;
		// gmst[gmst < 0] <- gmst[gmst < 0] + 24.
		if (gmst < 0)
			gmst += 24;

		// Local mean sidereal time
		double lmst = gmst + longitude / 15;
		lmst = lmst % 24;
		// lmst[lmst < 0] <- lmst[lmst < 0] + 24.
		if (lmst < 0)
			lmst += 24;
		lmst = lmst * 15 * deg2rad;

		// Hour angle
		double ha = lmst - ra;
		// ha[ha < -pi] <- ha[ha < -pi] + twopi;
		if (ha < -Math.PI) // I think
			ha += twopi;
		// HA[HA > PI] <- HA[HA > PI] - TWOPI;
		if (ha > Math.PI)
			ha -= twopi;

		// Latitude to radians
		latitude = latitude * deg2rad;

		// Azimuth and elevation
		double elAsinInput = Math.sin(dec) * Math.sin(latitude) + Math.cos(dec) * Math.cos(latitude) * Math.cos(ha);
		// System.out.println("elAsinInput:" + elAsinInput);
		double el = Math.asin(Math.sin(dec) * Math.sin(latitude) + Math.cos(dec) * Math.cos(latitude) * Math.cos(ha));
		double az = Math.asin((0 - Math.cos(dec)) * Math.sin(ha) / Math.cos(el));
		double elc = Math.asin(Math.sin(dec) / Math.sin(latitude));
		// az[el >= elc] <- pi - az[el >= elc];
		if (el >= elc)
			az = Math.PI - az;
		// az[el <= elc & ha > 0] <- az[el <= elc & ha > 0] + twopi;
		if (el <= elc && ha > 0)
			az += twopi;

		el = el / deg2rad;
		az = az / deg2rad;
		latitude = latitude / deg2rad;

		// return(list(elevation=el, azimuth=az));

		elevation = el;
		azimuth = az;

		// System.out.println("elevation: " + el);
		// System.out.println("azimuth: " + az);
	}

	public double getElevation() {
		return elevation;
	}

	public double getAzimuth() {
		return azimuth;
	}

}