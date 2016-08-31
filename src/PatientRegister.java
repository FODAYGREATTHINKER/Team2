package com.example.medicalbookingapp;

import java.util.HashMap;
import java.util.MissingResourceException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class PatientRegister extends Activity {
	// variable declarations
	Button back, register;
	EditText namein, usernamein, passwordin, passwordagian, Agein;
	TextView textview2, registernotice;
	CheckBox femalein, malein;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patientregister);

		// back button
		back = (Button) findViewById(R.id.back);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});

		// register when register button pressed and gets data from elements
		register = (Button) findViewById(R.id.Signup);
		namein = (EditText) findViewById(R.id.namein);
		usernamein = (EditText) findViewById(R.id.usernamein);
		passwordin = (EditText) findViewById(R.id.passwordin);
		passwordagian = (EditText) findViewById(R.id.passwordagain);
		Agein = (EditText) findViewById(R.id.editText1);
		femalein = (CheckBox) findViewById(R.id.checkBox1);
		malein = (CheckBox) findViewById(R.id.checkBox2);
		registernotice = (TextView) findViewById(R.id.registernotice);

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// boolean variable if user has wrong inputs or empty inputs.
				boolean userexception = false;

				Logindatabase ldb = new Logindatabase(getBaseContext());

				String name = String.valueOf(namein.getText().toString());
				Integer age = 0;
				try {
					age = Integer.valueOf(Agein.getText().toString());
				} catch (NumberFormatException ex) {
					userexception = true;
				}
				String username = String.valueOf(usernamein.getText()
						.toString());
				String password = String.valueOf(passwordin.getText()
						.toString());
				String passwordagian1 = String.valueOf(passwordagian.getText()
						.toString());

				String gender = "";
				if (femalein.isChecked()) {
					gender = "female";
				} else {
					gender = "male";
				}

				//checking inputs for user exceptions. if user exception is found it will set userexception boolean variable true.
				if (!(age > 1)) {
					registernotice.setText("Please enter valid age!");
					userexception = true;
				}
				if (!(password.equals(passwordagian1))) {
					registernotice.setText("password does not match!");
					userexception = true;
				}
				if (username.equals("") || password.equals("")) {
					registernotice.setText("please enter password");
					userexception = true;
				}
				if (!(femalein.isChecked() && femalein.isChecked())) {
					registernotice.setText("please fill all fields");
					userexception = true;
				}

				if (userexception == false) {

					// insert details
					ldb.insertpatient(name, age, gender, username, password);

					// goes back to main
					back.performClick();
				}
			}
		});

	}

}
