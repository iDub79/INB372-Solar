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

import solar.solarAndroid.*;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class SolarPowerCalculator extends Activity {
	String baseServletAddress = "http://inb372-solar.appspot.com/";
	//String baseServletAddress = "http://10.0.2.2:8888/";
	Location lastKnownLocation;
	LocationManager locationManager;
	LocationListener locationListener;
	
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
			Address address = addresses.get(0);
			String addressText = String.format("%s, %s, %s",
                    address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                    address.getLocality(),
                    address.getCountryName());
			((TextView)findViewById(R.id.Address)).setText(addressText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SetLocation() {
		SetLocation(this.findViewById(R.id.Location));
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
			@Override
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

			@Override
			public void onProviderDisabled(String provider) {
				//((EditText)findViewById(R.id.PanelOrientation)).append(provider + "di");
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				//((EditText)findViewById(R.id.PanelOrientation)).append(provider + "en");
				
			}

			@Override
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
		findViewById(R.id.rowAddress).setVisibility(View.GONE);
		findViewById(R.id.rowCoordinates).setVisibility(View.GONE);
		findViewById(R.id.rowSunlightHours).setVisibility(View.GONE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.GONE);
		findViewById(R.id.rowTariffValue).setVisibility(View.GONE);
		
		findViewById(R.id.Submit).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Savings).setVisibility(View.GONE);
		findViewById(R.id.tvSavings).setVisibility(View.GONE);
		
		((TextView)findViewById(R.id.CurrentTab)).setText("Panel Settings");
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
		findViewById(R.id.rowAddress).setVisibility(View.GONE);
		findViewById(R.id.rowCoordinates).setVisibility(View.GONE);
		findViewById(R.id.rowSunlightHours).setVisibility(View.GONE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.GONE);
		findViewById(R.id.rowTariffValue).setVisibility(View.GONE);
		
		findViewById(R.id.Submit).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Savings).setVisibility(View.GONE);
		findViewById(R.id.tvSavings).setVisibility(View.GONE);
		
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
		findViewById(R.id.rowAddress).setVisibility(View.VISIBLE);
		findViewById(R.id.rowCoordinates).setVisibility(View.VISIBLE);
		findViewById(R.id.rowSunlightHours).setVisibility(View.VISIBLE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.VISIBLE);
		findViewById(R.id.rowTariffValue).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Submit).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Savings).setVisibility(View.GONE);
		findViewById(R.id.tvSavings).setVisibility(View.GONE);
		
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
		findViewById(R.id.rowAddress).setVisibility(View.GONE);
		findViewById(R.id.rowCoordinates).setVisibility(View.GONE);
		findViewById(R.id.rowSunlightHours).setVisibility(View.GONE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.GONE);
		findViewById(R.id.rowTariffValue).setVisibility(View.GONE);
		
		findViewById(R.id.Submit).setVisibility(View.GONE);
		
		findViewById(R.id.Savings).setVisibility(View.VISIBLE);
		findViewById(R.id.tvSavings).setVisibility(View.VISIBLE);
		
		((TextView)findViewById(R.id.CurrentTab)).setText("Results");
	}
	
	//Panel efficiency is not a percentage but the maximum power output of the panel in watts
	private Float getPanelEfficiency() throws InvalidInputException {
		String panelEfficiencyText = ((EditText)findViewById(R.id.PanelEfficiency)).getText().toString();
		if (panelEfficiencyText.length() == 0) {
			throw new InvalidInputException("Require panel efficiency to be input.");
		}
		
		Float panelEfficiency = Float.parseFloat(panelEfficiencyText);
		if (panelEfficiency > 0) {
			return panelEfficiency;
		} else {
			// not sure whether I should return false or throw an exception
			throw new InvalidInputException("Panel efficiency must be positive.");
			//return null;
		}
	}
	
	private Float getInverterEfficiency() throws InvalidInputException {
		String inverterEfficiencyText = ((EditText)findViewById(R.id.InverterEfficiency)).getText().toString();
		if (inverterEfficiencyText.length() == 0) {
			throw new InvalidInputException("Require inverter efficiency to be input.");
		}
		
		Float inverterEfficiency = Float.parseFloat(inverterEfficiencyText);		// surround with try/catch maybe
		// inverter efficiency can be input either as a decimal representation of a percentage
		// or an integer
		if (inverterEfficiency >= 50 && inverterEfficiency <= 100) {
			return inverterEfficiency;
		} else if (inverterEfficiency >= 0.5 && inverterEfficiency <= 1.0) {
			return inverterEfficiency * 100;
		} else {
			// not sure whether I should return false or throw an exception
			throw new InvalidInputException("Inverter efficiency must be between 50% and 100%.");
			//return null;
		}
	}
	
	private Float getTariff() throws InvalidInputException{
		String tariffText = ((EditText)findViewById(R.id.TariffRate)).getText().toString();
		if (tariffText.length() == 0) {
			throw new InvalidInputException("Require tariff rate to be input.");
		}
		
		Float tariff = Float.parseFloat(tariffText);		// surround with try/catch maybe
		
		// Some weird place might have really high feed-in tariff returns
		if (tariff > 0 && tariff < 1) {	// value must have been input in dollars!
			return tariff * 100;
		}
		if (tariff >= 1 && tariff <= 500) {
			return tariff;
		} else {
			// not sure whether I should return false or throw an exception
			throw new InvalidInputException("Feed-in tariff rate must be between 0 and 500 cents");
			//return null;
		}
	}

	// what was this value again?
	private Integer getSunlight() throws InvalidInputException {
		String sunlightText = ((EditText)findViewById(R.id.SunlightHours)).getText().toString();
		if (sunlightText.length() == 0) {
			throw new InvalidInputException("Require sunlight to be input.");
		}
		
		Integer sunlight = Integer.parseInt(sunlightText);		// surround with try/catch maybe
		
		// Some weird place might have really high feed-in sunlight returns
		if (sunlight > 0) {	// value must have been input in dollars!
			return sunlight;
		} else {
			// not sure whether I should return false or throw an exception
			throw new InvalidInputException("Feed-in sunlight rate must be greater than 0");
			//return null;
		}
	}

	private Float getConsumption() throws InvalidInputException {
		String consumptionText = ((EditText)findViewById(R.id.PowerConsumption)).getText().toString();
		if (consumptionText.length() == 0) {
			throw new InvalidInputException("Require power consumption to be input.");
		}
		
		Float consumption = Float.parseFloat(consumptionText);		// surround with try/catch maybe
		
		// Some weird place might have really high feed-in consumption returns
		if (consumption >= 0) {	// value must have been input in dollars!
			return consumption;
		} else {
			// not sure whether I should return false or throw an exception
			throw new InvalidInputException("Consumption must be positive");
			//return null;
		}
	}

	private Float getAngle() throws InvalidInputException {
		String angleText = ((EditText)findViewById(R.id.PanelAngle)).getText().toString();
		if (angleText.length() == 0) {
			throw new InvalidInputException("Require angle to be input.");
		}
		
		Float angle = Float.parseFloat(angleText);		// surround with try/catch maybe
		
		if (angle >= 0 && angle <= 90) {	// value must have been input in dollars!
			return angle;
		} else {
			// not sure whether I should return false or throw an exception
			throw new InvalidInputException("Angle must be positive");
			//return null;
		}
	}

	// orientation will probably end up being a N, S, E, W combination or a number of degrees between 
	// 1 and 360 and will need to be converted to something consistent
	private String getOrientation() throws InvalidInputException {
		String orientationText = ((EditText)findViewById(R.id.PanelOrientation)).getText().toString();
		if (orientationText.length() == 0) {
			throw new InvalidInputException("Require orientation to be input.");
		}
		
		if (true) {		// replace with thing that filters the input and converts to consistent thing
			return orientationText;
		} else
			throw new InvalidInputException("Orientation is somehow invalid.");		// fix message
	}

	// Later will probably return latitude/longitude as some sort of struct and address will just be one
	// of the ways this information will be obtained
	private String getAddress() throws InvalidInputException {
		String addressText = ((TextView)findViewById(R.id.Address)).getText().toString();
		//if (addressText.length() == 0) {
		//	throw new InvalidInputException("Require address to be input.");
		//}
		
		if (true) {		// replace with thing that filters the input and converts to consistent thing
			return addressText;
		} else {
			throw new InvalidInputException("Address is somehow invalid.");		// fix message
		}
	}
	
	private Integer getPanelQuantity() throws InvalidInputException {
		String panelQuantityText = ((EditText)findViewById(R.id.PanelQuantity)).getText().toString();
		if (panelQuantityText.length() == 0) {
			throw new InvalidInputException("Require quantity to be input.");
		}
		
		Integer panelQuantity = Integer.parseInt(panelQuantityText); 
		
		if (panelQuantity > 0) {		// replace with thing that filters the input and converts to consistent thing
			return panelQuantity;
		} else {
			throw new InvalidInputException("Address is somehow invalid.");		// fix message
		}
	}
	
	private void originalSubmit(View view) throws InvalidInputException {		
    	try {
    		Float panelEfficiency = getPanelEfficiency();
    		Float inverterEfficiency = getInverterEfficiency();
    		String address = getAddress();
    		String orientation = getOrientation();
    		Float angle = getAngle();
    		Integer sunlight = getSunlight();
    		Float consumption = getConsumption();
    		Float tariff = getTariff();
    		Integer  panelQuantity = getPanelQuantity();
    		String panelManufacturer = ((Spinner)findViewById(R.id.PanelManufacturer)).getSelectedItem().toString();
    		String panelModel = ((Spinner)findViewById(R.id.PanelModel)).getSelectedItem().toString();
    		String inverterManufacturer = ((Spinner)findViewById(R.id.InverterManufacturer)).getSelectedItem().toString();
    		String inverterModel = ((Spinner)findViewById(R.id.InverterModel)).getSelectedItem().toString();
		
			// Create a new HttpClient and Post Header
			ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			//((EditText)findViewById(R.id.PanelOrientation)).setText("a");
			if (connec.getActiveNetworkInfo().isAvailable()) {
				//((EditText)findViewById(R.id.PanelOrientation)).setText("b");
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
		        
		        
		        nameValuePairs.add(new BasicNameValuePair("sunlight", sunlight.toString()));
		        nameValuePairs.add(new BasicNameValuePair("consumption", consumption.toString()));
		        nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString(lastKnownLocation.getLatitude())));
		        nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString(lastKnownLocation.getLongitude())));
		        nameValuePairs.add(new BasicNameValuePair("address", address.toString()));
		        nameValuePairs.add(new BasicNameValuePair("tariff", tariff.toString()));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("e");
		        JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
		        JSONObject savingsJSONObject = jObject.getJSONObject("Savings");
		        //((EditText)findViewById(R.id.PanelOrientation)).setText("f");
		        if (savingsJSONObject.getBoolean("Success")) {
		        	Double savings = savingsJSONObject.getDouble("Amount");
		        	savings = Math.round(savings * 100.0) / 100.0;
		        	DecimalFormat f = new DecimalFormat("#.00");
		        	((TextView)findViewById(R.id.Savings)).setText("$" + f.format(savings));
		        	resultsTab(view);
		        	findViewById(R.id.Results).setEnabled(true);
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
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				populatePanelModels(selected);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
		
		Spinner panelModelSpinner = (Spinner)findViewById(R.id.PanelModel);
		panelModelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				setPanelPowerOnModelSelected(selected);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
		
		PopulateInverterManufacturer();
		Spinner invManufacturerSpinner = (Spinner)findViewById(R.id.InverterManufacturer);
		invManufacturerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				populateInverterModels(selected);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
		
		Spinner invModelSpinner = (Spinner)findViewById(R.id.InverterModel);
		invModelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();
				setInverterEfficiencyOnModelSelected(selected);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
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
