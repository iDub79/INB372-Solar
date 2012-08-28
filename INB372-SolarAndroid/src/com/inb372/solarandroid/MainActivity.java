package com.inb372.solarandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        
    }
    
    // check what view matters
    public void submitDetails(View view) {
    	EditText editText = (EditText)findViewById(R.id.PanelSize);
    	//get value of all EditTexts and put into JSP
    	editText.setText("134");
    }


	//Might want a menu later
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/

    
}
