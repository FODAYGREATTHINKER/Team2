package com.example.medicalbookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Activity {

	TextView wname;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		wname = (TextView) findViewById(R.id.wname);
	
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("username");
		    wname.setText(value);
		    
		    //The key argument here must match that used in the other activity
		}
	
	
	}

	
}
