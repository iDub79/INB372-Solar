package solar;

import static java.lang.Math.*;

public class DistanceCalc {


	
	public DistanceCalc() {
		
	}
	
	public double findDistance(double lat1, double long1, double lat2, double long2) {
		final double earthRadius = 6371;
		double distBetween = 0;
		double diffLat = Math.toRadians(lat2-lat1);
		double diffLong = Math.toRadians(long2-long1);
		double rLat1 = Math.toRadians(lat1);
		double rLat2 = Math.toRadians(lat2);
		
		// a and c are splitting the equation up into pieces
		double a = sin(diffLat/2) * Math.sin(diffLat/2) +
				Math.cos(rLat1) * Math.Math.cos(rLat2) *
				sin(diffLong/2) * Math.sin(diffLong/2);
		double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		distBetween = earthRadius * c; //distance in km;
		return distBetween;
	}
	
	public int findClosest();
}
