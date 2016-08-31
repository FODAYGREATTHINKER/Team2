package com.example.medicalbookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Activity {
	//variable declaration from user welcome text
	TextView wname;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		wname = (TextView) findViewById(R.id.wname);
	
		//gets username with intent from main when login sucessfull
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("username");
		    wname.setText(value);
		    //username entered to this method so it will can be used to get the username of user 
		    getusername(value);
		    
		  }
	
	
	}
	
	//this method can be used for getting username for other methods
	public String getusername(String username){
		return username;
	}
	
}
