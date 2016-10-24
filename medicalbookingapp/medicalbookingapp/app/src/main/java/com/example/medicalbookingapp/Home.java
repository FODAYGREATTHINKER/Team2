package com.example.medicalbookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Home extends Activity {
	//variable declaration from user welcome text
	TextView wname;Button b;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		wname = (TextView) findViewById(R.id.wname);
		
		//Button that links pages with booking page.
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent Intent = new Intent(Home.this, BookingPage.class);
				startActivity(Intent);
				
			}
			
		});
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
