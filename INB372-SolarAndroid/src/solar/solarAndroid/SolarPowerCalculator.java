package solar.solarAndroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewSeries.GraphViewStyle;

import solar.solarAndroid.*;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;


public class SolarPowerCalculator extends Activity {
	String baseServletAddress = "http://inb372-solar.appspot.com/";
	//String baseServletAddress = "http://10.0.2.2:8888/";
	Location lastKnownLocation;
	LocationManager locationManager;
	LocationListener locationListener;
	
	SensorManager sensorManager;
	SensorEventListener sensorListener;
	
	boolean graphViewInstantiated = false;
	GraphView graphView;
	
	float actualOrientation = 0;
	float actualAngle = 27;
	
	private void RequestLocation() {
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		// Setting lastKnownLocation to the finest lastKnownLocation I have, otherwise setting to default
		if (lastKnownLocation == null) {
			Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (loc == null) {
				loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			if (loc != null) {
				lastKnownLocation = loc;
			} else {
				lastKnownLocation = new Location("Default");
				lastKnownLocation.setLatitude(-27);
				lastKnownLocation.setLongitude(153);
			}
				
		}
	}
	
	Address address;
	
	public void SetLocation(View view) {
		Double roundedLatitude = Math.round(lastKnownLocation.getLatitude() * 100.0) / 100.0;
        Double roundedLongitude = Math.round(lastKnownLocation.getLongitude() * 100.0) / 100.0;
        String locationAsString = Double.toString(Math.abs(roundedLatitude));
        if (roundedLatitude < 0)
        	locationAsString += "°S, ";
        else
        	locationAsString += "°N, ";
        
        locationAsString += Double.toString(Math.abs(roundedLongitude));
        
        if (roundedLongitude < 0)
        	locationAsString += "°W";
        else
        	locationAsString += "°E";
        
        ((EditText)findViewById(R.id.Coordinates)).setText(locationAsString);
        Geocoder geocoder = new Geocoder(this);
        try {
			List<Address> addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
			address = addresses.get(0);
			String addressText = String.format("%s, %s, %s",
                    address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                    address.getLocality(),
                    address.getCountryName());
			((TextView)findViewById(R.id.Address)).setText(addressText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //((Button)findViewById(R.id.PanelOrientation)).setText(address.getSubLocality());
	}
	
	public void SetLocation() {
		SetLocation(this.findViewById(R.id.Location));
	}
	
	Sensor magFields;
	Sensor accels;
	
	float m_lastMagFields[];
	float m_lastAccels[];
	
	float orientation = 0;
	String orientationText = "North";
	float angle = 27;
	
	// called by panelAngle and panelOrientation methods
	public void setPanelAngleOrientation(View view) {
		((Button)findViewById(R.id.PanelAngle)).setText(Float.toString(angle));
		
		((Button)findViewById(R.id.PanelOrientation)).setText(orientationText);
		
		actualAngle = angle;
		actualOrientation = orientation;
	}
	
	// method that gets the angle and orientation that the phone is probably currently at
	public void computeOrientation() {
		float rot[] = new float[9];
		float I[] = new float[9];
		float orientationValues[] = new float[3];
		SensorManager.getRotationMatrix(rot, I, m_lastAccels, m_lastMagFields);
		SensorManager.getOrientation(rot, orientationValues);
		
		double radiansToDegrees = (double) (180.0 / Math.PI);
		
		double Orientation = Math.round(orientationValues[0] * radiansToDegrees * 100.0) / 100.0;
		if (Orientation < 0)
			Orientation = (360.0 + Orientation);
		orientation = (float)Orientation;
		
		angle = (float)(Math.round(Math.abs(orientationValues[1] * radiansToDegrees) * 100.0) / 100.0);
		
		
		
		//((EditText)findViewById(R.id.PanelOrientation)).setText("Z: " + Math.round(orientationValues[0] * radiansToDegrees)  + " X: " + Math.round(orientationValues[1] * radiansToDegrees) + " Y: " + Math.round(orientationValues[2] * radiansToDegrees));
		//((EditText)findViewById(R.id.PanelOrientation)).setText("or: " + orientation  + " ang: " + angle);
		
		// separate to on button click
		
		if (orientation <= 45 || orientation > 315)
			orientationText = "North";
		
		else if (orientation <= 135)
			orientationText = "East";
		
		else if (orientation <= 225)
			orientationText = "South";
		
		else if (orientation <= 315)
			orientationText = "West";
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        
        sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        accels = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magFields = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorListener = new SensorEventListener() {

			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
			
			

			public void onSensorChanged(SensorEvent event) {
				if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					if (m_lastAccels == null) {
			            m_lastAccels = new float[3];
			        }
		 
			        System.arraycopy(event.values, 0, m_lastAccels, 0, 3);
			 
			        if (m_lastMagFields != null) {
			            computeOrientation();
			        }
				} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
					if (m_lastMagFields == null) {
						m_lastMagFields = new float[3];
					}
		 
			        System.arraycopy(event.values, 0, m_lastMagFields, 0, 3);
			 
			        if (m_lastAccels != null) {
			            computeOrientation();
			        }
		        }
				
			}
        	
        };
        sensorManager.registerListener(sensorListener, magFields, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorListener, accels, SensorManager.SENSOR_DELAY_NORMAL);
        
        //	SensorMan
        
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				//((EditText)findViewById(R.id.PanelOrientation)).setText("abc");
				lastKnownLocation = location;
				// soon as a GPS or network provider says the location changed, stop updating until it's clicked again
				// not sure whether GPS works yet, since it's raining now and I can't stand out in the rain waiting for it to work
				// network does work though, however network requires credit
				locationManager.removeUpdates(locationListener);
				
				// Insert check if location is visible (don't want to change it if someone is looking at it)
				SetLocation();
				//output.
				
			}

			public void onProviderDisabled(String provider) {
				//((EditText)findViewById(R.id.PanelOrientation)).append(provider + "di");
				
			}

			public void onProviderEnabled(String provider) {
				//((EditText)findViewById(R.id.PanelOrientation)).append(provider + "en");
				
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				//((EditText)findViewById(R.id.PanelOrientation)).setText(provider);
				
			}
		};
		
		RequestLocation();
        SetLocation();
        
        // this stuff will be used somehow later
        /*AutoCompleteTextView addressView = (AutoCompleteTextView)findViewById(R.id.Address);
        ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(this, android.R.layout.simple_dropdown_item_1line);
        addressView.setAdapter(adapter);*/
    }
    
    public void panelTab(View view) {
    	// show panel stuff
    	findViewById(R.id.rowPanelManufacturer).setVisibility(View.VISIBLE);
    	findViewById(R.id.rowPanelModel).setVisibility(View.VISIBLE);
    	findViewById(R.id.rowPanelEfficiency).setVisibility(View.VISIBLE);
    	findViewById(R.id.rowPanelQuantity).setVisibility(View.VISIBLE);
    	findViewById(R.id.rowPanelAngle).setVisibility(View.VISIBLE);
    	findViewById(R.id.rowPanelOrientation).setVisibility(View.VISIBLE);
    	
    	// hide everything else
    	// inverter page
    	findViewById(R.id.rowInverterManufacturer).setVisibility(View.GONE);
    	findViewById(R.id.rowInverterModel).setVisibility(View.GONE);
		findViewById(R.id.rowInverterEfficiency).setVisibility(View.GONE);
		
		// other page
		//findViewById(R.id.rowAddress).setVisibility(View.GONE);
		findViewById(R.id.rowCoordinates).setVisibility(View.GONE);
		//findViewById(R.id.rowSunlightHours).setVisibility(View.GONE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.GONE);
		findViewById(R.id.rowTariffValue).setVisibility(View.GONE);
		
		findViewById(R.id.Submit).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Savings).setVisibility(View.GONE);
		findViewById(R.id.tvSavings).setVisibility(View.GONE);
		if (graphViewInstantiated)
			graphView.setVisibility(View.GONE);
    }
    
    public void inverterTab(View view) {
    	// show panel stuff
    	findViewById(R.id.rowPanelManufacturer).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelModel).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelEfficiency).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelQuantity).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelAngle).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelOrientation).setVisibility(View.GONE);
    	
    	// inverter page
    	findViewById(R.id.rowInverterManufacturer).setVisibility(View.VISIBLE);
    	findViewById(R.id.rowInverterModel).setVisibility(View.VISIBLE);
		findViewById(R.id.rowInverterEfficiency).setVisibility(View.VISIBLE);
		
		// other page
		//findViewById(R.id.rowAddress).setVisibility(View.GONE);
		findViewById(R.id.rowCoordinates).setVisibility(View.GONE);
		//findViewById(R.id.rowSunlightHours).setVisibility(View.GONE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.GONE);
		findViewById(R.id.rowTariffValue).setVisibility(View.GONE);
		
		findViewById(R.id.Submit).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Savings).setVisibility(View.GONE);
		findViewById(R.id.tvSavings).setVisibility(View.GONE);
		if (graphViewInstantiated)
			graphView.setVisibility(View.GONE);
		
		((TextView)findViewById(R.id.CurrentTab)).setText("Inverter Settings");
		findViewById(R.id.Submit).setEnabled(true);
		findViewById(R.id.Location).setEnabled(true);
		
    }
    
	public void locationTab(View view) {
		// show panel stuff
    	findViewById(R.id.rowPanelManufacturer).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelModel).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelEfficiency).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelQuantity).setVisibility(View.GONE);

    	findViewById(R.id.rowPanelAngle).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelOrientation).setVisibility(View.GONE);
    	
    	// inverter page
    	findViewById(R.id.rowInverterManufacturer).setVisibility(View.GONE);
    	findViewById(R.id.rowInverterModel).setVisibility(View.GONE);
		findViewById(R.id.rowInverterEfficiency).setVisibility(View.GONE);
		
		// other page
		//findViewById(R.id.rowAddress).setVisibility(View.VISIBLE);

		findViewById(R.id.rowCoordinates).setVisibility(View.VISIBLE);

		//findViewById(R.id.rowSunlightHours).setVisibility(View.VISIBLE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.VISIBLE);
		findViewById(R.id.rowTariffValue).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Submit).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Savings).setVisibility(View.GONE);
		findViewById(R.id.tvSavings).setVisibility(View.GONE);
		if (graphViewInstantiated)
			graphView.setVisibility(View.GONE);
		
		((TextView)findViewById(R.id.CurrentTab)).setText("Other Settings");
	}

	public void resultsTab(View view) {

		// panel page
		findViewById(R.id.rowPanelManufacturer).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelModel).setVisibility(View.GONE);

    	findViewById(R.id.rowPanelEfficiency).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelQuantity).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelAngle).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelOrientation).setVisibility(View.GONE);
    	
    	// inverter page
    	findViewById(R.id.rowInverterManufacturer).setVisibility(View.GONE);
    	findViewById(R.id.rowInverterModel).setVisibility(View.GONE);
		findViewById(R.id.rowInverterEfficiency).setVisibility(View.GONE);
		
		// other page
		//findViewById(R.id.rowAddress).setVisibility(View.GONE);
		findViewById(R.id.rowCoordinates).setVisibility(View.GONE);
		//findViewById(R.id.rowSunlightHours).setVisibility(View.GONE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.GONE);
		findViewById(R.id.rowTariffValue).setVisibility(View.GONE);
		
		findViewById(R.id.Submit).setVisibility(View.GONE);
		
		findViewById(R.id.Savings).setVisibility(View.VISIBLE);
		findViewById(R.id.tvSavings).setVisibility(View.VISIBLE);
		if (graphViewInstantiated)
			graphView.setVisibility(View.VISIBLE);
		
		((TextView)findViewById(R.id.CurrentTab)).setText("Results");
	}
	
	// 1 = January (method translates down to 0)
	public static double getMonthsElectricity(JSONArray electricityJSONArray, int index, int firstDayIndex, int lastDayIndex) {
		double total = 0;
		for (int i = firstDayIndex; i < lastDayIndex; i++) {
			try {
				JSONArray daysElectricity = electricityJSONArray.getJSONArray(i);
				total += daysElectricity.getDouble(index);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return total;
	}
	
	/*private GraphViewData[] getMonthlyElectricityGraphViewSeries(JSONArray electricityJSONArray, int index) {
		GraphViewData[] graphViewData = new GraphViewData[12];
		graphViewData[0] = new GraphViewData(1, getMonthsElectricity(electricityJSONArray, index, 0, 31));
		int leap = 0;
		if (electricityJSONArray.length() == 366) {
			leap = 1;
		}
		graphViewData[1] = new GraphViewData(2, getMonthsElectricity(electricityJSONArray, index, 31, 59 + leap));
		graphViewData[2] = new GraphViewData(3, getMonthsElectricity(electricityJSONArray, index, 59 + leap, 90 + leap));
		graphViewData[3] = new GraphViewData(4, getMonthsElectricity(electricityJSONArray, index, 90 + leap, 120 + leap));
		graphViewData[4] = new GraphViewData(5, getMonthsElectricity(electricityJSONArray, index, 120 + leap, 151 + leap));
		graphViewData[5] = new GraphViewData(6, getMonthsElectricity(electricityJSONArray, index, 151 + leap, 181 + leap));
		graphViewData[6] = new GraphViewData(7, getMonthsElectricity(electricityJSONArray, index, 181 + leap, 212 + leap));
		graphViewData[7] = new GraphViewData(8, getMonthsElectricity(electricityJSONArray, index, 212 + leap, 243 + leap));
		graphViewData[8] = new GraphViewData(9, getMonthsElectricity(electricityJSONArray, index, 243 + leap, 273 + leap));
		graphViewData[9] = new GraphViewData(10, getMonthsElectricity(electricityJSONArray, index, 273 + leap, 304 + leap));
		graphViewData[10] = new GraphViewData(11, getMonthsElectricity(electricityJSONArray, index, 304 + leap, 334 + leap));
		graphViewData[11] = new GraphViewData(12, getMonthsElectricity(electricityJSONArray, index, 334 + leap, 365 + leap));
		//GraphViewSeries theSeries = new GraphViewSeries(graphViewData);
		
		return graphViewData;
	}*/
	
	private GraphViewData[] getMonthlyElectricityGraphViewSeriesFromDaily(JSONArray electricityJSONArray, int index) {
		GraphViewData[] graphViewData = new GraphViewData[12];
		graphViewData[0] = new GraphViewData(1, getMonthsElectricity(electricityJSONArray, index, 0, 31));
		int leap = 0;
		if (electricityJSONArray.length() == 366) {
			leap = 1;
		}
		graphViewData[1] = new GraphViewData(2, getMonthsElectricity(electricityJSONArray, index, 31, 59 + leap));
		graphViewData[2] = new GraphViewData(3, getMonthsElectricity(electricityJSONArray, index, 59 + leap, 90 + leap));
		graphViewData[3] = new GraphViewData(4, getMonthsElectricity(electricityJSONArray, index, 90 + leap, 120 + leap));
		graphViewData[4] = new GraphViewData(5, getMonthsElectricity(electricityJSONArray, index, 120 + leap, 151 + leap));
		graphViewData[5] = new GraphViewData(6, getMonthsElectricity(electricityJSONArray, index, 151 + leap, 181 + leap));
		graphViewData[6] = new GraphViewData(7, getMonthsElectricity(electricityJSONArray, index, 181 + leap, 212 + leap));
		graphViewData[7] = new GraphViewData(8, getMonthsElectricity(electricityJSONArray, index, 212 + leap, 243 + leap));
		graphViewData[8] = new GraphViewData(9, getMonthsElectricity(electricityJSONArray, index, 243 + leap, 273 + leap));
		graphViewData[9] = new GraphViewData(10, getMonthsElectricity(electricityJSONArray, index, 273 + leap, 304 + leap));
		graphViewData[10] = new GraphViewData(11, getMonthsElectricity(electricityJSONArray, index, 304 + leap, 334 + leap));
		graphViewData[11] = new GraphViewData(12, getMonthsElectricity(electricityJSONArray, index, 334 + leap, 365 + leap));
		//GraphViewSeries theSeries = new GraphViewSeries(graphViewData);
		
		return graphViewData;
	}
	
	
	private void SetElectricityGraph(JSONArray electricityJSONArray) {
		//((EditText)findViewById(R.id.PanelOrientation)).setText("q");
		
		//GraphViewSeries generatedSeries = new GraphViewSeries("Total generated", Color.rgb(0xEA, 0xA2, 0x28), getMonthlyElectricityGraphViewSeriesFromDaily(electricityJSONArray, 0));	// the line that should be there (throwing error)
		GraphViewSeries generatedSeries = new GraphViewSeries("Total generated", new GraphViewStyle(Color.rgb(0xEA, 0xEA, 0x28), 3), getMonthlyElectricityGraphViewSeriesFromDaily(electricityJSONArray, 0));
		
		GraphViewSeries excessSeries = new GraphViewSeries("Put Back to Grid", null, getMonthlyElectricityGraphViewSeriesFromDaily(electricityJSONArray, 1));
		
		
		graphView.addSeries(generatedSeries);		//generated
		graphView.addSeries(excessSeries);		//excess
		
			
	}
	
	private void originalSubmit(View view) throws InvalidInputException {		
    	try {
    		Float panelEfficiency = InputChecking.getPanelEfficiency(((EditText)findViewById(R.id.PanelEfficiency)).getText().toString());
    		Float inverterEfficiency = InputChecking.getInverterEfficiency(((EditText)findViewById(R.id.InverterEfficiency)).getText().toString());
    		String address = InputChecking.getAddress(((EditText)findViewById(R.id.Address)).getText().toString());
    		Button panelOrientationButton = ((Button)findViewById(R.id.PanelOrientation));
    		if (panelOrientationButton.getText().toString().equals("Click to set"))
    			panelOrientationButton.setText(orientationText);
    		String orientation = InputChecking.getOrientation(Float.toString(actualOrientation));
    		Button panelAngleButton = ((Button)findViewById(R.id.PanelAngle));
    		if (panelAngleButton.getText().toString().equals("Click to set"))
    			panelAngleButton.setText(Float.toString(actualAngle));
    		Float angle = InputChecking.getAngle(Float.toString(actualAngle));
    		//Integer sunlight = getSunlight();
    		Float consumption = InputChecking.getAngle(((EditText)findViewById(R.id.PowerConsumption)).getText().toString());
    		Float tariff = InputChecking.getTariff(((Spinner)findViewById(R.id.TariffRate)).getSelectedItem().toString());
    		Integer  panelQuantity = InputChecking.getPanelQuantity(((EditText)findViewById(R.id.PanelQuantity)).getText().toString());
    		String panelManufacturer = InputChecking.getPanelManufacturer(((Spinner)findViewById(R.id.PanelManufacturer)).getSelectedItem().toString());
    		String panelModel = InputChecking.getPanelModel(((Spinner)findViewById(R.id.PanelModel)).getSelectedItem().toString());
    		String inverterManufacturer = InputChecking.getInverterManufacturer(((Spinner)findViewById(R.id.InverterManufacturer)).getSelectedItem().toString());
    		String inverterModel = InputChecking.getInverterModel(((Spinner)findViewById(R.id.InverterModel)).getSelectedItem().toString());
		
			// Create a new HttpClient and Post Header
			ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connec.getActiveNetworkInfo().isAvailable()) {
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost(baseServletAddress + "solarServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
		
		        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		        nameValuePairs.add(new BasicNameValuePair("panelManufacturer", panelManufacturer));
		        nameValuePairs.add(new BasicNameValuePair("panelModel", panelModel));
		        nameValuePairs.add(new BasicNameValuePair("panelEfficiency", panelEfficiency.toString()));
		        nameValuePairs.add(new BasicNameValuePair("panelQty", panelQuantity.toString()));
		        nameValuePairs.add(new BasicNameValuePair("orientation", orientation.toString()));
		        nameValuePairs.add(new BasicNameValuePair("angle", angle.toString()));
		        
		        nameValuePairs.add(new BasicNameValuePair("inverterManufacturer", inverterManufacturer));
		        nameValuePairs.add(new BasicNameValuePair("inverterModel", inverterModel));
		        nameValuePairs.add(new BasicNameValuePair("inverterEfficiency", inverterEfficiency.toString()));
		        
		        
		        //nameValuePairs.add(new BasicNameValuePair("sunlight", sunlight.toString()));
		        nameValuePairs.add(new BasicNameValuePair("consumption", consumption.toString()));
		        nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString(lastKnownLocation.getLatitude())));
		        nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString(lastKnownLocation.getLongitude())));
		        nameValuePairs.add(new BasicNameValuePair("address", address.toString()));
		        nameValuePairs.add(new BasicNameValuePair("tariff", tariff.toString()));
		        
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("c");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("d");
		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("e");
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("e");
		        JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
		        JSONObject savingsJSONObject = jObject.getJSONObject("Savings");
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("f");
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("f");
		        if (savingsJSONObject.getBoolean("Success")) {
		        	Double savings = savingsJSONObject.getDouble("Amount");
		        	savings = Math.round(savings * 100.0) / 100.0;
		        	DecimalFormat f = new DecimalFormat("#.00");
		        	((TextView)findViewById(R.id.Savings)).setText("$" + f.format(savings));
		        	resultsTab(view);
		        	findViewById(R.id.Results).setEnabled(true);
		        	
		        	//graphView = new LineGraphView(this, "Electricity Generated");
		            
		            //graphView.setVisibility(View.GONE);
		            graphViewInstantiated = true;
		        	
		        	graphView = new LineGraphView(this, "Electricity Generated");
		     		graphView.setShowLegend(true);
		     		graphView.setLegendAlign(LegendAlign.TOP);
		     		graphView.setHorizontalLabels(new String[] {"Jan", "Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});
		     		
		        	SetElectricityGraph(savingsJSONObject.getJSONArray("DailyGen"));
		        	TableLayout layout = (TableLayout)findViewById(R.id.layout);
		        	layout.addView(graphView);
		        	
		        	//((EditText)findViewById(R.id.PanelOrientation)).setText("g");
		        	//((EditText)findViewById(R.id.PanelOrientation)).setText("g");
		        }
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("h");
			}
    	} catch(InvalidInputException e) {
    		// do stuffs
    		e.printStackTrace();
    	} catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void submitDetails(View view) throws InvalidInputException {
		//TableLayout layout = (TableLayout)findViewById(R.id.layout);
		//layout.removeView(graphView);
		//graphView.removeAllViews();
		//graphView.removeSeries(0);
		//graphView.removeSeries(1);
		if (graphViewInstantiated) {
			TableLayout layout = (TableLayout)findViewById(R.id.layout);
			layout.removeView(graphView);
			graphViewInstantiated = false;
		}
			
		
    	originalSubmit(view);
    }
	
	
	// Called when the application "starts", populates the panelManufacturer spinner
	private void PopulatePanelManufacturer() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("option", "getPanelManufacturers"));
		ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec.getActiveNetworkInfo().isAvailable()) {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(baseServletAddress + "panelServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
		    try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			
				// Execute HTTP Post Request
				HttpResponse response;
				
				response = httpclient.execute(httppost);
				
				JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
				
				JSONArray jArray = jObject.getJSONArray("Panels");
				String[] manufacturers = new String[jArray.length()];
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject manu = (JSONObject)jArray.get(i);
					manufacturers[i] = manu.getString("manufacturer");
				}
				
				Spinner spinner = (Spinner)findViewById(R.id.PanelManufacturer);
				
				ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, manufacturers);
				spinner.setAdapter(adapter);
				
		    } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	private void populatePanelModels(String manufacturer) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("option", "getPanelModels"));
		nameValuePairs.add(new BasicNameValuePair("manufacturer", manufacturer));
		ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec.getActiveNetworkInfo().isAvailable()) {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(baseServletAddress + "panelServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
		    try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			
				// Execute HTTP Post Request
				HttpResponse response;
				
				response = httpclient.execute(httppost);
				
				JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
				
				JSONArray jArray = jObject.getJSONArray("Panels");
				String[] models = new String[jArray.length()];
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject model = (JSONObject)jArray.get(i);
					models[i] = model.getString("model");
				}
				
				Spinner spinner = (Spinner)findViewById(R.id.PanelModel);
				
				ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, models);
				spinner.setAdapter(adapter);
				
		    } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void setPanelPowerOnModelSelected(String model) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("option", "getPanelPower"));
		nameValuePairs.add(new BasicNameValuePair("model", model));
		nameValuePairs.add(new BasicNameValuePair("manufacturer", ((Spinner)findViewById(R.id.PanelManufacturer)).getSelectedItem().toString()));		// needs checking, not used atm at all I don't think
		ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec.getActiveNetworkInfo().isAvailable()) {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(baseServletAddress + "panelServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
		    try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			
				// Execute HTTP Post Request
				HttpResponse response;
				
				response = httpclient.execute(httppost);
				
				JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
				
				JSONArray jArray = jObject.getJSONArray("Panels");		// this will probably not be an array later
				String[] powers = new String[jArray.length()];
				for (int i = 0; i < jArray.length(); i++) {	
					JSONObject power = (JSONObject)jArray.get(i);
					powers[i] = power.getString("power");
				}
				
				((EditText)findViewById(R.id.PanelEfficiency)).setText(powers[0]);
				
		    } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
    public void onStart() {
		super.onStart();
		PopulatePanelManufacturer();
		Spinner panelManufacturerSpinner = (Spinner)findViewById(R.id.PanelManufacturer);
		panelManufacturerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				populatePanelModels(selected);
			}
			
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
		
		Spinner panelModelSpinner = (Spinner)findViewById(R.id.PanelModel);
		panelModelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				setPanelPowerOnModelSelected(selected);
			}
			
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
		
		PopulateTariffRules();
		Spinner tariffRulesSpinner = (Spinner)findViewById(R.id.TariffRate);
		/*tariffRulesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				//String selected = parentView.getItemAtPosition(position).toString();
				//populatePanelModels(selected);
			}
			
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});*/
		
		
		
		PopulateInverterManufacturer();
		Spinner invManufacturerSpinner = (Spinner)findViewById(R.id.InverterManufacturer);
		invManufacturerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				populateInverterModels(selected);
			}
			
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
		
		Spinner invModelSpinner = (Spinner)findViewById(R.id.InverterModel);
		invModelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				setInverterEfficiencyOnModelSelected(selected);
			}
			
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
    }
	
	private void PopulateTariffRules() {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("option", "state"));
		nameValuePairs.add(new BasicNameValuePair("state", "Queensland"));		// hardcoded to queensland, since for the moment all the testing will take place in queensland and android is local features only
		ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec.getActiveNetworkInfo().isAvailable()) {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(baseServletAddress + "tariffServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
		    try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			
				// Execute HTTP Post Request
				HttpResponse response;
				
				response = httpclient.execute(httppost);
				
				JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
				
				JSONArray jArray = jObject.getJSONArray("Tariffs");
				String[] tariffRules = new String[jArray.length()];
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject manu = (JSONObject)jArray.get(i);
					tariffRules[i] = Long.toString(Math.round(manu.getDouble("TariffRate") * 100));			// tariffRate only in spinner, fix this to include description and only use rate to feed back to solarServlet
				}
				
				Spinner spinner = (Spinner)findViewById(R.id.TariffRate);
				
				ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, tariffRules);
				spinner.setAdapter(adapter);
				
		    } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Called when the application "starts", populates the InverterManufacturer spinner
		private void PopulateInverterManufacturer() {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("option", "getInverterManufacturers"));
			ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connec.getActiveNetworkInfo().isAvailable()) {
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost(baseServletAddress + "inverterServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
			    try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				
					// Execute HTTP Post Request
					HttpResponse response;
					
					response = httpclient.execute(httppost);
					
					JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
					
					JSONArray jArray = jObject.getJSONArray("Inverters");
					String[] manufacturers = new String[jArray.length()];
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject manu = (JSONObject)jArray.get(i);
						manufacturers[i] = manu.getString("manufacturer");
					}
					
					Spinner spinner = (Spinner)findViewById(R.id.InverterManufacturer);
					
					ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, manufacturers);
					spinner.setAdapter(adapter);
					
			    } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			
		private void populateInverterModels(String manufacturer) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("option", "getInverterModels"));
			nameValuePairs.add(new BasicNameValuePair("manufacturer", manufacturer));
			ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connec.getActiveNetworkInfo().isAvailable()) {
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost(baseServletAddress + "inverterServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
			    try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				
					// Execute HTTP Post Request
					HttpResponse response;
					
					response = httpclient.execute(httppost);
					
					JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
					
					JSONArray jArray = jObject.getJSONArray("Inverters");
					String[] models = new String[jArray.length()];
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject model = (JSONObject)jArray.get(i);
						models[i] = model.getString("model");
					}
					
					Spinner spinner = (Spinner)findViewById(R.id.InverterModel);
					
					ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, models);
					spinner.setAdapter(adapter);
					
			    } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private void setInverterEfficiencyOnModelSelected(String model) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("option", "getInverterEfficiency"));
			nameValuePairs.add(new BasicNameValuePair("model", model));
			nameValuePairs.add(new BasicNameValuePair("manufacturer", ((Spinner)findViewById(R.id.InverterManufacturer)).getSelectedItem().toString()));		// needs checking, not used atm at all I don't think
			ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connec.getActiveNetworkInfo().isAvailable()) {
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost(baseServletAddress + "inverterServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
			    try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				
					// Execute HTTP Post Request
					HttpResponse response;
					
					response = httpclient.execute(httppost);
					
					JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
					
					JSONArray jArray = jObject.getJSONArray("Inverters");		// this will probably not be an array later
					String[] efficiencies = new String[jArray.length()];
					for (int i = 0; i < jArray.length(); i++) {	
						JSONObject efficiency = (JSONObject)jArray.get(i);
						efficiencies[i] = efficiency.getString("efficiency");
					}
					
					((EditText)findViewById(R.id.InverterEfficiency)).setText(efficiencies[0]);
					
			    } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

    
}
