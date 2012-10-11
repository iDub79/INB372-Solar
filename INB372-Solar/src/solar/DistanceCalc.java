package solar;

import static java.lang.Math.*;
import java.util.ArrayList;

public class DistanceCalc {

	private ArrayList<double[]> stationLocations = new ArrayList<double[]>();	
	private ArrayList<double[]> stationReadings = new ArrayList<double[]>();
	
	double[] brisbaneLocation = {-27.471011f, 153.023449f};
	double[] brisbaneReadings = { 3.3f, 8.3f, 6.1f, 7.1f, 5.8f, 2.4f, 3.6f,
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
	
	
	double[] sydneyLocation = {-33.867487f, 151.20699f};
	double[] sydneyReadings = { 9.5f, 8f, 3.1f, 4.2f, 6.4f, 8.6f, 7f, 8.5f,
			7.4f, 6.5f, 4f, 7.1f, 6f, 8.4f, 5.3f, 7.1f, 8.4f, 6f, 6f, 8.6f,
			9.1f, 9f, 8f, 5.5f, 8.4f, 7.8f, 7.2f, 4.2f, 8.1f, 9f, 8.8f, 8.8f,
			8.4f, 4.8f, 7.8f, 8.4f, 3.9f, 4.1f, 4.9f, 6.6f, 6.3f, 7.1f, 2.3f,
			2.7f, 3.4f, 4.9f, 4.6f, 6.2f, 5f, 6.8f, 6.4f, 4.6f, 4.4f, 5.7f,
			8.1f, 6.8f, 7.7f, 3.6f, 4.9f, 4f, 2.9f, 6.9f, 7.4f, 3.8f, 4.5f,
			6.8f, 7.3f, 4.6f, 3.3f, 4f, 6.2f, 6.9f, 3f, 4.5f, 5.8f, 4.4f, 3f,
			3f, 4.2f, 3.6f, 5.8f, 5.9f, 6.3f, 5.2f, 3f, 2.8f, 4.4f, 5.5f, 5.1f,
			1.9f, 5.1f, 4f, 5.8f, 5.4f, 3.6f, 3.8f, 5.1f, 5.1f, 5.7f, 3.4f, 5f,
			5.2f, 4.8f, 5.4f, 4.7f, 2.1f, 3f, 5f, 4.6f, 2.9f, 4.9f, 4.5f, 2.3f,
			3.8f, 1.9f, 2.8f, 3f, 2.9f, 3f, 2.7f, 4.5f, 2.4f, 1.9f, 4.3f, 4.1f,
			4.2f, 4.4f, 4.4f, 3.8f, 4.2f, 2.7f, 4.3f, 3.7f, 3.9f, 4.1f, 4.2f,
			3.9f, 4f, 3.6f, 3.9f, 3.8f, 3.5f, 3.5f, 2.3f, 2.3f, 2.7f, 3.1f,
			2.8f, 2.3f, 1.3f, 1.9f, 2.4f, 2.6f, 3.5f, 2.1f, 3.5f, 3.3f, 3.2f,
			3.6f, 3.4f, 2.7f, 2.7f, 2.2f, 2.1f, 1.6f, 2.1f, 2.1f, 3.3f, 3.4f,
			3.4f, 3.4f, 3f, 3.4f, 3.3f, 3.4f, 3.4f, 3.4f, 3.2f, 1.9f, 2.4f,
			2.1f, 2.6f, 3.3f, 3.4f, 2.9f, 3.5f, 3.2f, 3.4f, 3.6f, 3.5f, 3.5f,
			3.6f, 3.6f, 1.3f, 3.6f, 2.5f, 2.9f, 2.4f, 3.3f, 2.9f, 1.8f, 0.8f,
			1.1f, 2.9f, 3.2f, 2.7f, 3.1f, 3.3f, 3.6f, 3.8f, 3.8f, 3.5f, 3.5f,
			3.6f, 2.6f, 3.8f, 3.6f, 3.1f, 1.6f, 3f, 2.4f, 3.7f, 3f, 2.5f, 3.5f,
			2.6f, 2.8f, 3.7f, 1.7f, 4.1f, 1.6f, 3.7f, 3.1f, 3.1f, 2.9f, 4.3f,
			4.8f, 4.4f, 2.4f, 4f, 4.7f, 3.1f, 4.3f, 3.3f, 3f, 4.4f, 5.2f, 4.6f,
			5.2f, 5.2f, 2.5f, 1.5f, 4.9f, 5f, 5.6f, 5.9f, 5.9f, 5.2f, 4.8f,
			5.8f, 5.8f, 5.4f, 5.6f, 6.2f, 6.3f, 6.1f, 1.8f, 1.3f, 5f, 5.8f,
			2.2f, 4.3f, 6f, 4.7f, 2.8f, 6f, 5.4f, 5.7f, 3.1f, 5.6f, 3.9f, 4.7f,
			6f, 6.2f, 5.8f, 6.1f, 1.8f, 5.9f, 7.5f, 6.8f, 6.8f, 7.8f, 7.8f,
			7.7f, 7.2f, 6.9f, 7.7f, 2.1f, 2f, 3.4f, 5.9f, 4.6f, 5.3f, 7.8f,
			5.2f, 7.3f, 4.3f, 7.8f, 8.6f, 7f, 6.7f, 7.4f, 7.8f, 5.5f, 8.6f,
			7.8f, 7.1f, 7.9f, 8.8f, 4.1f, 3.9f, 7.4f, 8.8f, 6.8f, 5.1f, 3.6f,
			3.5f, 3.7f, 3.8f, 6.4f, 8.5f, 9.2f, 7.6f, 5.7f, 7.5f, 7.7f, 9.1f,
			5.5f, 6.2f, 5.3f, 6.3f, 6.2f, 8.3f, 6.1f, 6.1f, 4.1f, 6.9f, 6.9f,
			7.4f, 5.8f, 7.3f, 7f, 4.3f, 6.9f, 6.1f, 5.4f, 7.4f, 8.3f, 9.1f, 6f,
			5.6f, 7.9f, 7.7f, 7f, 5.9f };

	
	double[] darwinLocation = {-12.46282f, 130.841769f};
	double[] darwinReadings = { 6.5f, 6.9f, 5.1f, 7.9f, 7.6f, 2.3f, 4.4f, 4.5f,
			6.1f, 3.1f, 2.1f, 5.5f, 6.6f, 7.1f, 3.6f, 3.4f, 5.2f, 3.2f, 6.8f,
			7f, 2.2f, 2.5f, 4.6f, 2.2f, 4.7f, 6.9f, 7.7f, 7.5f, 8.1f, 8.3f,
			7.1f, 7f, 7.4f, 6.9f, 4.7f, 5.2f, 4.7f, 7.5f, 8f, 4.8f, 6.5f, 7f,
			2.9f, 1.4f, 2f, 5.5f, 1.7f, 4.1f, 4.7f, 7.9f, 5.1f, 5.5f, 4.6f,
			4.5f, 6.8f, 6.8f, 7.5f, 7.3f, 6.6f, 3.8f, 5.4f, 3.5f, 5.5f, 3.7f,
			2.8f, 4.5f, 5.6f, 3f, 6.6f, 6.5f, 7f, 4.8f, 6.6f, 6.9f, 6.7f, 3.3f,
			7.1f, 7.4f, 6.3f, 5.7f, 6.5f, 7f, 6.6f, 6f, 6.1f, 6.3f, 4.3f, 3.1f,
			3.6f, 6.1f, 6.3f, 6.7f, 7.1f, 5.6f, 5.6f, 6.4f, 6.6f, 6f, 6.7f,
			6.7f, 3.8f, 6.2f, 6.4f, 7.1f, 7.1f, 7.2f, 6.8f, 6.7f, 6.8f, 6.9f,
			6.8f, 6.9f, 6.5f, 6.9f, 6.8f, 6.7f, 6.7f, 6.5f, 6.6f, 6.5f, 6.6f,
			6.4f, 6f, 5.3f, 6.3f, 5.6f, 6.4f, 6.6f, 6.4f, 6.4f, 6.4f, 6.3f,
			6.2f, 6.2f, 6.2f, 6.3f, 6.3f, 6f, 6.3f, 6.3f, 6.3f, 6.2f, 6.1f,
			6.1f, 6.3f, 6.3f, 6.3f, 6.2f, 6.2f, 6.1f, 6f, 5.9f, 5.8f, 5.8f, 6f,
			6.1f, 6.2f, 6.2f, 6.2f, 6.1f, 5.9f, 6f, 6f, 6f, 5.9f, 6f, 6f, 5.3f,
			5.6f, 5.8f, 5.9f, 5.9f, 5.8f, 5.9f, 5.8f, 5.7f, 5.8f, 5.8f, 5.8f,
			5.8f, 5.8f, 6f, 5.6f, 5.7f, 4.8f, 5.9f, 5.8f, 5.4f, 5.7f, 5.9f, 6f,
			5.9f, 2.5f, 6f, 5.6f, 6.1f, 6.2f, 6.2f, 6.1f, 6.2f, 6.2f, 6.3f,
			6.3f, 6.4f, 6.1f, 6.2f, 6.2f, 6.1f, 5.6f, 5.5f, 6.1f, 6.3f, 6.3f,
			6.4f, 6.4f, 6.4f, 6.4f, 6.4f, 6.4f, 6f, 6.8f, 7f, 7.1f, 7.1f, 7.1f,
			7.1f, 7f, 7f, 6.9f, 6.9f, 6.9f, 7.1f, 6.9f, 6.8f, 6.8f, 7.1f, 7.1f,
			6.8f, 6.5f, 7f, 6.7f, 7.5f, 7.6f, 7.7f, 7.5f, 7.6f, 7.1f, 6.3f,
			6.8f, 7.2f, 7.1f, 7f, 7f, 7.5f, 7.5f, 7.4f, 7.6f, 7.7f, 7.3f, 7.6f,
			6.8f, 7.5f, 7.6f, 7.6f, 7.6f, 7.2f, 7.4f, 7.4f, 4.9f, 7.5f, 7.6f,
			6.3f, 7.2f, 7.6f, 7.5f, 6f, 6.4f, 7.7f, 7.4f, 7.6f, 7.3f, 5.3f,
			5.1f, 3.9f, 5.6f, 6.5f, 7.6f, 5.9f, 5.8f, 6.8f, 6.4f, 3.1f, 8f,
			7.9f, 7.6f, 8f, 6.8f, 7.6f, 6.2f, 7.7f, 6.6f, 7.5f, 6.8f, 7.2f,
			6.1f, 5.2f, 8.2f, 7.7f, 7.2f, 8f, 7.8f, 7.2f, 7.3f, 7.7f, 6.7f,
			8.2f, 8.1f, 5.5f, 8.2f, 7.9f, 8.2f, 8.3f, 8f, 7.4f, 7.5f, 6.9f,
			7.6f, 8.3f, 7.5f, 4.2f, 8.2f, 6.8f, 8.1f, 8.1f, 7.2f, 7.1f, 4.8f,
			5.2f, 7f, 6.9f, 6f, 5f, 5.4f, 6.4f, 5.8f, 6.5f, 5.9f };
	
	
	double[] canberraLocation = {-35.308235f, 149.124224f};
	double[] canberraReadings = { 9.4f, 6.2f, 4.5f, 6.1f, 7.7f, 7.2f, 7.7f,
			7.4f, 4.4f, 6.3f, 2.9f, 4.8f, 2.5f, 4.1f, 9.1f, 8.8f, 9.5f, 8.9f,
			7.9f, 7.5f, 9f, 6.1f, 8.9f, 6.7f, 8.4f, 8.5f, 7.7f, 8.9f, 9.2f, 9f,
			7.5f, 7.3f, 5.5f, 6.4f, 3.6f, 8.5f, 6.3f, 6.5f, 7.4f, 2.3f, 4f,
			7.6f, 8.3f, 5f, 2.3f, 6.8f, 6.1f, 4f, 8.2f, 8f, 8.2f, 8.3f, 7.8f,
			6.2f, 7.7f, 2.2f, 4.8f, 6.5f, 5.7f, 7.9f, 7.9f, 5.8f, 6f, 7.6f,
			6.7f, 4.3f, 2.6f, 3.8f, 6.4f, 6.9f, 4f, 5.1f, 6f, 5.2f, 3.9f, 4.4f,
			3.4f, 1.7f, 5.2f, 5.4f, 5.3f, 5.7f, 5.7f, 5.9f, 6.1f, 5.8f, 5.2f,
			5.9f, 6.1f, 5.9f, 4.5f, 5.3f, 4.3f, 5.8f, 5.1f, 2.4f, 4.9f, 2.9f,
			2.6f, 5.2f, 5f, 4.8f, 5.2f, 5.2f, 4.4f, 2.4f, 4.7f, 3.8f, 4.9f,
			4.8f, 3.9f, 3.3f, 3.9f, 3.7f, 3.8f, 4.3f, 1.9f, 3.3f, 3.6f, 4.4f,
			4.2f, 4.2f, 4.2f, 4.1f, 4.3f, 3.6f, 4f, 3.7f, 3.6f, 4.1f, 2.7f,
			3.8f, 3.9f, 3.6f, 2.9f, 3.5f, 3.4f, 2f, 2.9f, 3.7f, 3.7f, 3.5f,
			3.5f, 3.2f, 2.4f, 2.8f, 3.2f, 1.5f, 3.1f, 2.9f, 2.6f, 3.4f, 2.8f,
			2.7f, 3.3f, 2.6f, 3.1f, 2.9f, 2.7f, 2.9f, 3.1f, 3.2f, 3.1f, 2.3f,
			1.7f, 2.9f, 3.2f, 3.1f, 3.2f, 3.3f, 3.2f, 3.3f, 2.9f, 2.3f, 2.8f,
			2.4f, 2.8f, 2.1f, 2.1f, 3.3f, 2.7f, 3.4f, 2.8f, 3.4f, 3.4f, 2.4f,
			3.4f, 3.5f, 2.4f, 2.7f, 2.2f, 2.2f, 2f, 2.3f, 2.8f, 3.6f, 1.9f,
			1.4f, 3.2f, 3.4f, 3.6f, 3.6f, 3.5f, 2.6f, 3.4f, 3.4f, 2.9f, 0.8f,
			2.7f, 2.3f, 3.2f, 2.7f, 1.9f, 3.7f, 2.9f, 3.4f, 2.8f, 3.5f, 1f,
			1.4f, 3.5f, 3.4f, 3.1f, 3.3f, 4.2f, 4.7f, 4.8f, 4.7f, 4.2f, 4.7f,
			4.4f, 4.9f, 5.2f, 5.2f, 4.2f, 3.9f, 1.5f, 5.3f, 4.7f, 3.5f, 5.1f,
			5.1f, 5.8f, 5.8f, 4.9f, 5.9f, 5.2f, 5.8f, 6.1f, 5.8f, 4.3f, 6.2f,
			6.4f, 6.4f, 2f, 4.6f, 5.9f, 6.3f, 1.7f, 3.9f, 2.4f, 4.4f, 6.8f, 7f,
			5.2f, 1.7f, 3.7f, 5.3f, 4.9f, 5.6f, 5.7f, 7.4f, 6.5f, 5.1f, 6f,
			7.7f, 7.9f, 7.7f, 7.8f, 7.8f, 7.7f, 5.5f, 7.6f, 6.9f, 1.6f, 3f,
			4.3f, 5.9f, 3.7f, 7.5f, 4.6f, 6.3f, 8.5f, 8.6f, 6.9f, 7.3f, 5.1f,
			7f, 7.4f, 8.7f, 8.3f, 8.4f, 8.8f, 9f, 4.7f, 7.1f, 8.1f, 8.3f, 4.4f,
			7.8f, 4.4f, 7.2f, 5.4f, 3.9f, 6.9f, 8.9f, 8.8f, 7.3f, 8.9f, 8.2f,
			9.5f, 7.5f, 8.1f, 7.4f, 8.8f, 7.3f, 8.5f, 8.4f, 5.9f, 6.9f, 6.8f,
			7.8f, 8.4f, 6.4f, 9.1f, 6.9f, 4.5f, 7.5f, 6.9f, 7.1f, 8.4f, 8.4f,
			8.3f, 4.9f, 5.9f, 5.9f, 9.3f };

	
	double[] melbourneLocation = {-37.811367f, 144.971829f};
	double[] melbourneReadings = { 5.7f, 8.7f, 8.7f, 9.8f, 9.1f, 9.2f, 8f, 7.7f,
			3.9f, 2.7f, 3.8f, 3.9f, 2.1f, 5.6f, 9.2f, 8.6f, 4.2f, 6.2f, 7.2f,
			9.4f, 8.1f, 7.6f, 8.9f, 7.1f, 7.3f, 4.8f, 9.1f, 8.9f, 9f, 7.1f,
			8.2f, 5.6f, 2.7f, 1.9f, 8.2f, 8.7f, 7.7f, 8.1f, 4.5f, 2f, 8f, 6.7f,
			7.8f, 7.6f, 4.6f, 7.1f, 5.3f, 7.2f, 5.9f, 6f, 5.7f, 7.7f, 7.6f, 6f,
			6.4f, 2.3f, 2.2f, 6.2f, 6.2f, 5.4f, 4.2f, 7.6f, 7.4f, 7.2f, 3.1f,
			2.4f, 2.9f, 6.6f, 6.6f, 4.1f, 7f, 6.8f, 6.6f, 4f, 4.2f, 6.5f, 6f,
			2.9f, 2.6f, 5.4f, 3f, 4f, 2.8f, 4f, 5.8f, 5.8f, 5.7f, 4.2f, 2.8f,
			3.9f, 5.4f, 5.4f, 5.5f, 5.5f, 3.6f, 4.3f, 4.6f, 2.9f, 2.2f, 3.5f,
			4.3f, 4.8f, 4.7f, 4.8f, 4.1f, 1.9f, 4.4f, 4.1f, 3.4f, 4f, 4.4f,
			4.1f, 4.3f, 4.3f, 4.1f, 1.9f, 3.6f, 3.6f, 3.2f, 2.1f, 2.9f, 2.6f,
			2.4f, 3.3f, 2.8f, 2.2f, 2.1f, 2.4f, 2.6f, 2.1f, 2.4f, 2.7f, 2.6f,
			2.9f, 2.8f, 3f, 2f, 2.1f, 2.9f, 2.9f, 2.2f, 2.2f, 1.9f, 1.9f, 3.1f,
			3f, 2f, 2.8f, 2.9f, 2.7f, 1.5f, 2.4f, 2.7f, 2.9f, 2.9f, 2.6f, 2.7f,
			2.6f, 2.6f, 2.3f, 2.5f, 2.5f, 2.1f, 1.9f, 2.3f, 1.6f, 2.3f, 2.2f,
			2.7f, 2.7f, 2.6f, 2.6f, 2.9f, 2.8f, 2.9f, 1.2f, 2.4f, 1.7f, 1.8f,
			2.6f, 1.6f, 2.9f, 1.7f, 1.9f, 1.8f, 2.2f, 2.9f, 3.1f, 2.9f, 2.1f,
			2.1f, 2.7f, 0.9f, 1.7f, 3.1f, 2.6f, 2f, 1.1f, 2.3f, 3.1f, 3f, 3.1f,
			2.2f, 2.9f, 2.9f, 0.9f, 1.2f, 2.3f, 1.9f, 2.6f, 1.3f, 1.3f, 2.4f,
			3f, 1.8f, 3f, 1.1f, 2.9f, 0.8f, 1.5f, 2.7f, 4.1f, 3.9f, 4.2f, 4.2f,
			4.3f, 2.9f, 3.8f, 3.3f, 4.2f, 3.2f, 4.1f, 4.8f, 4.6f, 1.7f, 4.8f,
			2f, 3.9f, 3.8f, 3.6f, 3.4f, 4.6f, 2.7f, 4.6f, 3.6f, 3.7f, 5.1f,
			4.5f, 5.4f, 5.6f, 3.6f, 3.2f, 5.8f, 5.1f, 4.4f, 6.1f, 5.9f, 3.6f,
			0.9f, 2.9f, 2.4f, 5.8f, 6.7f, 6f, 4.1f, 4.8f, 3.8f, 3.5f, 4.2f,
			3.9f, 4.3f, 6f, 7.1f, 6.9f, 6.1f, 6.6f, 7.3f, 7.5f, 7.4f, 5.1f,
			1.6f, 2.4f, 6.7f, 0.9f, 4.8f, 7.9f, 7.2f, 4.5f, 6f, 3.2f, 2.8f,
			3.7f, 6.8f, 8.6f, 4.7f, 8.1f, 5.7f, 6.3f, 7.1f, 8.5f, 8.8f, 4.2f,
			7.8f, 8.1f, 8.5f, 8.4f, 7.7f, 3.6f, 7.9f, 8.2f, 6.8f, 8.8f, 9.2f,
			5.5f, 4.1f, 7.4f, 6.4f, 5.4f, 6.9f, 9.4f, 7.2f, 6.7f, 9.4f, 9.3f,
			9.4f, 9.4f, 8.1f, 7.1f, 6.6f, 7.9f, 8.8f, 8.6f, 9.5f, 9.6f, 8.4f,
			4.9f, 5.8f, 8.6f, 7.1f, 9.6f, 8.9f, 8.9f, 6.7f, 6.4f, 8.7f, 9.1f,
			8.7f };

	
	double[] perthLocation = {-31.932854f, 115.86194f};
	double[] perthReadings = { 9.4f, 9.1f, 6.6f, 9.3f, 4f, 9.1f, 9.2f, 9.5f,
			9.5f, 9.4f, 9.6f, 9.6f, 8.8f, 8.5f, 9.5f, 9.4f, 7.9f, 9.5f, 9.2f,
			9.3f, 7.1f, 7.4f, 5.9f, 3.8f, 9.3f, 9.3f, 8.4f, 8.4f, 5.9f, 9f,
			8.9f, 8.7f, 8.6f, 8.9f, 8.9f, 8.8f, 8.5f, 8.6f, 8.6f, 7.5f, 8.6f,
			8.3f, 7.9f, 8.2f, 7.3f, 8f, 8.1f, 8.1f, 3f, 8.1f, 8.3f, 8f, 6.4f,
			7.2f, 7.8f, 7.3f, 7.3f, 7.6f, 7.8f, 7.7f, 7.5f, 7f, 7.7f, 7.2f,
			7.5f, 7.6f, 7.3f, 7.4f, 7f, 7.3f, 6.9f, 7.1f, 6.9f, 7.1f, 7f, 6.9f,
			6.9f, 6.5f, 6.3f, 6.7f, 6.7f, 6.7f, 6.6f, 6.6f, 6.5f, 6.2f, 6.3f,
			6.4f, 6.3f, 6.2f, 6.1f, 5.9f, 4.9f, 6f, 6f, 5.6f, 5.5f, 5.3f, 5.4f,
			5.5f, 5.7f, 5.3f, 3.3f, 4.6f, 5.5f, 4.1f, 5.1f, 4.8f, 4.6f, 3.6f,
			4.9f, 4.9f, 3.6f, 4.4f, 5f, 4.8f, 4.7f, 4.8f, 4.4f, 4.3f, 4.7f,
			4.7f, 4.2f, 4.4f, 4.5f, 4.5f, 4.5f, 4.4f, 3f, 2.5f, 3.5f, 2.9f,
			4.1f, 4.2f, 1.9f, 4.1f, 3.9f, 4.1f, 3f, 3.2f, 3.3f, 3.9f, 3.9f,
			3.6f, 2.9f, 3.6f, 3.6f, 3f, 3.9f, 3.7f, 3.6f, 3.7f, 3.8f, 3.4f,
			3.4f, 3.6f, 2.3f, 2.1f, 2.6f, 3.2f, 3f, 2.4f, 2.8f, 3.2f, 2.9f,
			3.6f, 2.4f, 3.2f, 3.2f, 2.8f, 3.3f, 1.3f, 2.8f, 2.9f, 2.8f, 3.1f,
			3.5f, 3.5f, 3.7f, 3.3f, 3.4f, 3.2f, 1.9f, 1.6f, 3.6f, 3.5f, 3.8f,
			1.9f, 3.8f, 3.8f, 3.8f, 3.6f, 0.3f, 3.5f, 2.8f, 2.6f, 2.8f, 0.9f,
			3.1f, 3.1f, 2.3f, 2.7f, 2.4f, 2.9f, 2.9f, 3.9f, 3.9f, 3.9f, 3.6f,
			3f, 3.3f, 4f, 3.8f, 3.8f, 3.2f, 3.1f, 3.4f, 3.9f, 3.6f, 4.3f, 4.7f,
			4.6f, 3.6f, 3.8f, 4.9f, 4.5f, 4.4f, 4.9f, 5.1f, 4.7f, 4.8f, 4.8f,
			2.4f, 3.9f, 4f, 5.1f, 5.7f, 5.7f, 5.8f, 5.9f, 5.9f, 6f, 6f, 4.6f,
			4.7f, 2.5f, 4.5f, 5.1f, 4.3f, 4.3f, 4f, 2.6f, 4.5f, 6.4f, 6.1f,
			5.5f, 4.4f, 5.5f, 6.6f, 6.9f, 5.8f, 5.8f, 4.3f, 5.8f, 6.6f, 7.2f,
			6.7f, 6f, 5.9f, 5.2f, 7f, 5.9f, 6.2f, 5.6f, 7f, 7.2f, 5.2f, 3.9f,
			8f, 8f, 8f, 2.4f, 6.4f, 6.1f, 4.4f, 1.9f, 6.6f, 6.8f, 8.2f, 7.9f,
			6.3f, 1.8f, 6.8f, 5.6f, 6.2f, 7.1f, 7.3f, 7.9f, 8.2f, 8.4f, 8.6f,
			6.8f, 7.4f, 9f, 8.9f, 7.2f, 8.4f, 9.1f, 9.1f, 9.1f, 9.2f, 9.2f,
			8.4f, 8.6f, 8.6f, 7.8f, 8.4f, 9.2f, 9.2f, 9.2f, 9.1f, 7.6f, 8.5f,
			3.9f, 7.6f, 6.9f, 6.9f, 9.3f, 7.4f, 5.6f, 5.8f, 7.9f, 7.8f, 9.2f,
			9.4f, 9.3f, 9.4f, 9.5f, 9.6f, 9.5f, 9.2f, 9.6f, 9.6f, 9.7f, 8.2f,
			8.4f, 6.1f };

	
	double[] adelaideLocation = {-34.928621f, 138.599959f};
	double[] adelaideReadings = { 6.9f, 9.7f, 9.8f, 8.6f, 9.2f, 8.3f, 9.1f,
			4.5f, 9.2f, 9.2f, 4.7f, 4.6f, 2.3f, 9.3f, 9.4f, 9.4f, 7.5f, 9.4f,
			9.5f, 9.5f, 9.2f, 9.2f, 9f, 7.5f, 5.8f, 9f, 8.4f, 9.2f, 8.9f, 8.7f,
			8.2f, 4.6f, 6.3f, 4.2f, 9.1f, 9.1f, 8.8f, 8.3f, 6.4f, 4.1f, 5f,
			8.7f, 7.9f, 7.6f, 8.2f, 7.2f, 1.6f, 6.7f, 6.6f, 7.8f, 8.3f, 8.1f,
			8f, 6.4f, 4.6f, 7.7f, 7.1f, 6.8f, 7.9f, 7.7f, 7.7f, 7.7f, 7.7f,
			5.5f, 2.3f, 5f, 5f, 7.2f, 6.9f, 4.2f, 6.9f, 6.8f, 7f, 5.6f, 4.6f,
			6.7f, 2.9f, 3.3f, 4.6f, 2.6f, 3.9f, 5.2f, 4.7f, 5.1f, 6.2f, 6.1f,
			6f, 5.8f, 6f, 5.9f, 5.9f, 5.9f, 5.8f, 5.7f, 2.2f, 4f, 4.4f, 4.1f,
			3.7f, 3.4f, 5f, 5.3f, 5.3f, 4.8f, 3.1f, 4.3f, 3.6f, 4.5f, 3.5f,
			4.9f, 4.8f, 4.8f, 4.5f, 4.5f, 1.6f, 1.9f, 4.4f, 4.4f, 3.3f, 3.2f,
			4.2f, 4f, 3.5f, 3.4f, 2.9f, 3.1f, 2.9f, 3.3f, 2.8f, 2.9f, 3.9f,
			3.9f, 3.5f, 3.1f, 3.2f, 2.4f, 1.6f, 2.8f, 2.7f, 2.5f, 2.8f, 2.9f,
			2.8f, 3f, 3.6f, 3.3f, 1.9f, 3.3f, 3.1f, 2.1f, 2.4f, 3f, 2.3f, 2.4f,
			2.4f, 2.6f, 2.8f, 3.2f, 3.3f, 2.8f, 2.6f, 2.4f, 2.6f, 2.3f, 2.9f,
			2.4f, 2f, 3.2f, 3.2f, 2.4f, 3.2f, 3.4f, 3.2f, 3.1f, 0.8f, 3.2f,
			1.9f, 2.7f, 2.1f, 2.8f, 2.8f, 2.4f, 2f, 2.1f, 2.3f, 2.4f, 3.4f, 3f,
			3.4f, 1.8f, 3.2f, 2.8f, 1.6f, 3.6f, 3.6f, 2.8f, 2.5f, 3.1f, 2.6f,
			3.4f, 3.4f, 1.9f, 3.1f, 3.4f, 1.9f, 0.7f, 3.2f, 3.4f, 3f, 3.2f,
			2.8f, 2.6f, 3.6f, 3.6f, 3.2f, 3.6f, 0.9f, 1f, 3.7f, 2.8f, 3.2f,
			3.3f, 3.8f, 4.6f, 4.6f, 4.6f, 4.4f, 4.2f, 4.6f, 4.7f, 4.4f, 5f, 5f,
			3.4f, 4.9f, 5.3f, 4.4f, 4.5f, 3.7f, 3f, 3.6f, 4.3f, 4.6f, 5.7f,
			5.1f, 5.4f, 5.1f, 5.9f, 6f, 1.4f, 5.1f, 6f, 5.1f, 3.2f, 4.1f, 6.4f,
			5.5f, 6.1f, 3.9f, 6.3f, 5.9f, 6.9f, 6.9f, 2.1f, 1.9f, 4.6f, 5.3f,
			6.7f, 5.7f, 4.8f, 6.6f, 7.1f, 7f, 2.2f, 6.6f, 7.1f, 7.7f, 7.8f,
			7.6f, 2.4f, 3.2f, 7.3f, 4.4f, 3.2f, 8f, 7.7f, 7f, 2.3f, 7.1f, 8.1f,
			7.6f, 6.5f, 8.4f, 7.5f, 7.4f, 6.6f, 7.6f, 7.5f, 8.8f, 8.7f, 7.6f,
			7.6f, 8.7f, 8.3f, 8.7f, 8.9f, 7.6f, 6f, 8.8f, 8.3f, 7.5f, 7.5f, 9f,
			5.2f, 7.4f, 9.1f, 7.3f, 4.4f, 8.4f, 9.4f, 9.4f, 9.1f, 9.4f, 9.5f,
			9.6f, 4.9f, 5.1f, 7.7f, 8.8f, 7.9f, 8.6f, 8.6f, 9.3f, 8.6f, 4.5f,
			4.5f, 9.2f, 6.9f, 9.6f, 9.7f, 9.6f, 7.4f, 7.5f, 9.4f, 4.3f, 9.2f,
			9.4f };

	
	double[] hobartLocation = {-42.881903f, 147.323815f};
	double[] hobartReadings = { 8.9f, 7.9f, 7.9f, 6.4f, 5.4f, 8.1f, 5.8f, 6.1f,
			5.2f, 3.9f, 2.7f, 1.7f, 2.4f, 3.5f, 9.3f, 9.4f, 6.8f, 7.6f, 4.8f,
			8f, 4.8f, 7.6f, 8.9f, 8.6f, 5.4f, 7.1f, 7.6f, 8.3f, 6.6f, 2.3f,
			6.9f, 8.8f, 8.3f, 5.4f, 8.3f, 6.1f, 6.1f, 8.1f, 3.2f, 3.1f, 6.6f,
			6.8f, 8.1f, 8f, 5.8f, 3.3f, 6.1f, 2.1f, 6.6f, 6.4f, 5.9f, 7.6f,
			3.8f, 3.4f, 3.9f, 4.6f, 3.9f, 5f, 4.7f, 6f, 5.4f, 5.2f, 7.1f, 5.6f,
			4.1f, 3.4f, 3.2f, 5.2f, 6.6f, 5.6f, 6.6f, 5.4f, 5.4f, 5.9f, 5f,
			3.4f, 3.3f, 4.8f, 3.3f, 1.5f, 2.9f, 4.8f, 4f, 5.3f, 5.5f, 5.2f,
			3.3f, 3.5f, 2.4f, 3f, 4.7f, 5f, 5f, 5f, 3.2f, 3f, 2.3f, 1.4f, 2.2f,
			1.9f, 2.8f, 4.4f, 3.9f, 1.6f, 4f, 3.4f, 3.5f, 3.2f, 3.4f, 2.8f,
			3.8f, 3.6f, 3.7f, 3.7f, 3.2f, 1.7f, 1.9f, 3.3f, 1.5f, 2.5f, 3f, 3f,
			2.7f, 2.2f, 3f, 2.4f, 1.8f, 2.4f, 2.8f, 2.2f, 2.7f, 2.8f, 2.1f,
			2.3f, 2.3f, 2.8f, 2.3f, 1.7f, 1.5f, 2.6f, 2.5f, 2.1f, 2.6f, 2.4f,
			2.4f, 2.3f, 1.4f, 2f, 1.8f, 2.3f, 2.1f, 1f, 1.2f, 2.1f, 1.7f, 2.1f,
			2.3f, 2.2f, 2.3f, 1.9f, 1.6f, 2f, 1.4f, 1.9f, 1.6f, 1.9f, 2.2f,
			1.4f, 1.6f, 1.8f, 2.1f, 2.2f, 2.2f, 2.2f, 1.9f, 1.3f, 1.6f, 1.5f,
			1.8f, 1.7f, 1.5f, 1.3f, 1.4f, 1.8f, 1.7f, 0.9f, 1.9f, 2.3f, 2.4f,
			1.6f, 1.8f, 1.3f, 0.6f, 1.6f, 1.8f, 2.3f, 2.6f, 1.9f, 2f, 2.3f,
			1.9f, 1.6f, 2f, 2.4f, 1.8f, 1.3f, 0.6f, 1.1f, 1f, 0.6f, 0.8f, 1.1f,
			1.5f, 2.6f, 1f, 1.4f, 1.3f, 1.3f, 1f, 0.9f, 2.7f, 1.8f, 1.9f, 3.3f,
			3.5f, 3.4f, 2.7f, 3.5f, 2.5f, 2.4f, 3.1f, 2.6f, 4.2f, 2.5f, 3.1f,
			2.8f, 2.9f, 3.5f, 1.9f, 3.9f, 2.9f, 3.9f, 4.4f, 4.7f, 4.2f, 3.6f,
			4.1f, 3.9f, 5f, 4.8f, 4.7f, 4.9f, 4.8f, 4.4f, 4.6f, 4.4f, 5.4f,
			2.8f, 4.9f, 1.4f, 4.1f, 6.1f, 5.9f, 4.1f, 5.5f, 2.2f, 3.3f, 3.9f,
			3.7f, 3.8f, 5.6f, 5.7f, 5.4f, 6.3f, 3.4f, 4.9f, 6.6f, 7.1f, 7.1f,
			4.2f, 3f, 7.1f, 7.2f, 2.5f, 7.5f, 4.3f, 6.8f, 4.1f, 1.9f, 3.8f,
			6.3f, 6f, 7.1f, 8.3f, 7.3f, 7.9f, 5.3f, 5.6f, 7.3f, 7.7f, 8.5f,
			6.6f, 7.8f, 7.8f, 8.7f, 7f, 4.2f, 8.3f, 7.6f, 6.2f, 7.9f, 8.9f, 9f,
			9.1f, 3.8f, 6.9f, 5.7f, 6.9f, 8.4f, 7.3f, 8.1f, 6.7f, 5.9f, 7.9f,
			6.1f, 8.3f, 6.7f, 6.6f, 7.3f, 7.3f, 7.7f, 8.1f, 6.2f, 7.1f, 8.3f,
			8.7f, 4f, 7.6f, 9.9f, 9.7f, 7.6f, 7.8f, 6.2f, 8.7f, 6.9f, 8.4f,
			4.9f };

	
	
	public DistanceCalc() {
		stationLocations.add(brisbaneLocation);
		stationReadings.add(brisbaneReadings);

		stationLocations.add(sydneyLocation);
		stationReadings.add(sydneyReadings);
		
		stationLocations.add(darwinLocation);
		stationReadings.add(darwinReadings);
		
		stationLocations.add(canberraLocation);
		stationReadings.add(canberraReadings);
		
		stationLocations.add(melbourneLocation);
		stationReadings.add(melbourneReadings);
		
		stationLocations.add(perthLocation);
		stationReadings.add(perthReadings);
		
		stationLocations.add(adelaideLocation);
		stationReadings.add(adelaideReadings);
		
		stationLocations.add(hobartLocation);
		stationReadings.add(hobartReadings);
	}
	
	public double findDistance(double lat1, double long1, double lat2, double long2) {
		final double earthRadius = 6371;
		double distBetween = 0;
		double diffLat = Math.toRadians(lat2 - lat1);
		double diffLong = Math.toRadians(long2 - long1);
		double rLat1 = Math.toRadians(lat1);
		double rLat2 = Math.toRadians(lat2);
		
		// a and c are splitting the equation up into pieces
		double a = sin(diffLat / 2) * Math.sin(diffLat / 2) +
				Math.cos(rLat1) * Math.cos(rLat2) *
				sin(diffLong / 2) * Math.sin(diffLong / 2);
		double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		distBetween = earthRadius * c; //distance in km;
		return (double) distBetween;
	}
	
	public double[] findClosestStation(double userLat, double userLon){
		double prevDist = 10000000; // Make it really big so impossible to be further away
		double curDist;
		// for loop compares userLocation to each location in list, setting the station to be returned
		// value to list of sun rays if dist lower than previous
		int lowestIndex = 0;
		double lat, lon;
		for (int i = 0; i < stationLocations.size(); i++) {
			lat = stationLocations.get(i)[0];
			lon = stationLocations.get(i)[1];
			
			curDist = findDistance(userLat, userLon, lat, lon);
			
			if (curDist < prevDist) {
				curDist = prevDist;
				lowestIndex = i;
			}
		}
		return stationReadings.get(lowestIndex);
	}
}
