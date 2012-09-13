package solar.solarAndroid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import solar.solarAndroid.*;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SolarPowerCalculator extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        
    }
    
    public void systemTab(View view) {
    	findViewById(R.id.rowPanelSize).setVisibility(View.VISIBLE);
    	findViewById(R.id.rowPanelEfficiency).setVisibility(View.VISIBLE);
		findViewById(R.id.rowInverterEfficiency).setVisibility(View.VISIBLE);
		findViewById(R.id.rowAddress).setVisibility(View.GONE);
		findViewById(R.id.rowPanelOrientation).setVisibility(View.VISIBLE);
		findViewById(R.id.rowPanelAngle).setVisibility(View.VISIBLE);
		findViewById(R.id.rowSunlightHours).setVisibility(View.GONE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.GONE);
		findViewById(R.id.rowTariffValue).setVisibility(View.VISIBLE);
		findViewById(R.id.Submit).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Savings).setVisibility(View.GONE);
		findViewById(R.id.tvSavings).setVisibility(View.GONE);
    }
    
	public void locationTab(View view) {
		findViewById(R.id.rowPanelSize).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelEfficiency).setVisibility(View.GONE);
		findViewById(R.id.rowInverterEfficiency).setVisibility(View.GONE);
		findViewById(R.id.rowAddress).setVisibility(View.VISIBLE);
		findViewById(R.id.rowPanelOrientation).setVisibility(View.GONE);
		findViewById(R.id.rowPanelAngle).setVisibility(View.GONE);
		findViewById(R.id.rowSunlightHours).setVisibility(View.VISIBLE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.VISIBLE);
		findViewById(R.id.rowTariffValue).setVisibility(View.GONE);
		findViewById(R.id.Submit).setVisibility(View.VISIBLE);
		
		findViewById(R.id.Savings).setVisibility(View.GONE);
		findViewById(R.id.tvSavings).setVisibility(View.GONE);
	}

	public void resultsTab(View view) {
		findViewById(R.id.rowPanelSize).setVisibility(View.GONE);
    	findViewById(R.id.rowPanelEfficiency).setVisibility(View.GONE);
		findViewById(R.id.rowInverterEfficiency).setVisibility(View.GONE);
		findViewById(R.id.rowAddress).setVisibility(View.GONE);
		findViewById(R.id.rowPanelOrientation).setVisibility(View.GONE);
		findViewById(R.id.rowPanelAngle).setVisibility(View.GONE);
		findViewById(R.id.rowSunlightHours).setVisibility(View.GONE);
		findViewById(R.id.rowPowerConsumption).setVisibility(View.GONE);
		findViewById(R.id.rowTariffValue).setVisibility(View.GONE);
		findViewById(R.id.Submit).setVisibility(View.GONE);
		
		findViewById(R.id.Savings).setVisibility(View.VISIBLE);
		findViewById(R.id.tvSavings).setVisibility(View.VISIBLE);
		
		
	}
    
	// As more different methods of inputting panel size are added, this method will become more complex
	private Float getPanelSize() throws InvalidInputException {
		String panelSizeText = ((EditText)findViewById(R.id.PanelSize)).getText().toString();
		if (panelSizeText.length() == 0) {
			throw new InvalidInputException("Require panel size to be input.");
		}
		
		Float panelSize = Float.parseFloat(panelSizeText);
		if (panelSize > 0 && panelSize <= 100) {		// 100 is arbitrary maximum panel size
			return panelSize;
		} else {
			// not sure whether I should return false or throw an exception
			throw new InvalidInputException("Panel size must be positive and less than 100.");
			//return null;
		}
	}
	
	//Panel efficiency is not a percentage but the maximum power output of the panel in watts
	// As more different methods of inputting panel size are added, this method will become more complex
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
	
	// As more different methods of inputting panel size are added, this method will become more complex
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
		String addressText = ((EditText)findViewById(R.id.Address)).getText().toString();
		if (addressText.length() == 0) {
			throw new InvalidInputException("Require address to be input.");
		}
		
		if (true) {		// replace with thing that filters the input and converts to consistent thing
			return addressText;
		} else {
			throw new InvalidInputException("Address is somehow invalid.");		// fix message
		}
	}
	
	private void originalSubmit(View view) throws InvalidInputException {		
    	try {
    		Float panelSize = getPanelSize();
    		Float panelEfficiency = getPanelEfficiency();
    		Float inverterEfficiency = getInverterEfficiency();
    		String address = getAddress();
    		String orientation = getOrientation();
    		Float angle = getAngle();
    		Integer sunlight = getSunlight();
    		Float consumption = getConsumption();
    		Float tariff = getTariff();
		
			// Create a new HttpClient and Post Header
			ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connec.getActiveNetworkInfo().isAvailable()) {
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://10.0.2.2:8888/solarServlet");		// 10.0.2.2 magic thing that accesses localhost from emulator
		
		        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		        nameValuePairs.add(new BasicNameValuePair("panelSize", panelSize.toString()));
		        nameValuePairs.add(new BasicNameValuePair("panelEfficiency", panelEfficiency.toString()));
		        nameValuePairs.add(new BasicNameValuePair("inverterEfficiency", inverterEfficiency.toString()));
		        nameValuePairs.add(new BasicNameValuePair("orientation", orientation.toString()));
		        nameValuePairs.add(new BasicNameValuePair("angle", angle.toString()));
		        nameValuePairs.add(new BasicNameValuePair("sunlight", sunlight.toString()));
		        nameValuePairs.add(new BasicNameValuePair("consumption", consumption.toString()));
		        nameValuePairs.add(new BasicNameValuePair("address", address.toString()));
		        nameValuePairs.add(new BasicNameValuePair("tariff", tariff.toString()));
		        
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);

		        JSONObject jObject = new JSONObject(EntityUtils.toString(response.getEntity()));
		        JSONObject savingsJSONObject = jObject.getJSONObject("Savings");
		        
		        if (savingsJSONObject.getBoolean("Success")) {
		        	Double savings = savingsJSONObject.getDouble("Amount");
		        	savings = Math.round(savings * 100.0) / 100.0;
		        	DecimalFormat f = new DecimalFormat("#.00");
		        	((TextView)findViewById(R.id.Savings)).setText("$" + f.format(savings));
		        	resultsTab(view);
		        	findViewById(R.id.Results).setEnabled(true);
		        }
			}
    	} catch(InvalidInputException e) {
    		// do stuffs
    		throw e;
    	} catch (Exception e) {
	    	Log.e(e.getClass().toString(), e.getStackTrace().toString());
	    }
	}

	public void submitDetails(View view) throws InvalidInputException {
    	originalSubmit(view);
    }

    
}
