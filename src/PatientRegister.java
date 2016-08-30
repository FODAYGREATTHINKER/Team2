package com.example.medicalbookingapp;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PatientRegister extends Activity {
	Button back, register;
	EditText namein, usernamein, passwordin;
	TextView textview2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patientregister);

		
		// back button
		back = (Button) findViewById(R.id.back);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});

		// register when register button pressed and gets data from EditTexts
		register = (Button) findViewById(R.id.Signup);
		namein = (EditText) findViewById(R.id.namein);
		usernamein = (EditText) findViewById(R.id.usernamein);
		passwordin = (EditText) findViewById(R.id.passwordin);
		
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Logindatabase ldb=new Logindatabase(getBaseContext());
				
				String name = String.valueOf(namein.getText().toString());
				String username = String.valueOf(usernamein.getText().toString());
				String password = String.valueOf(passwordin.getText().toString());
				
				//insert details
				ldb.insertpatient(name,username,password);
				
			
				//goes back to main
				back.performClick();
			}
		});

	}
	
	
	
	
}
