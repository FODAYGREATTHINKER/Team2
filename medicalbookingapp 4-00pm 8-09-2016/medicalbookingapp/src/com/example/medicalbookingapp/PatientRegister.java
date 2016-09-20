package com.example.medicalbookingapp;

import java.util.HashMap;
import java.util.MissingResourceException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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
		// goes back to login screen
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
		femalein = (CheckBox) findViewById(R.id.checkBox2);
		malein = (CheckBox) findViewById(R.id.checkBox1);
		registernotice = (TextView) findViewById(R.id.registernotice);
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// assigns variables with user entered values
				String username = String.valueOf(usernamein.getText()
						.toString());
				String password = String.valueOf(passwordin.getText()
						.toString());
				String passwordagian1 = String.valueOf(passwordagian.getText()
						.toString());
				String name = String.valueOf(namein.getText().toString());

				// boolean variable if user has wrong inputs or empty inputs.

				/**
				 * checks userinput with the methods. if inputs are wrong
				 * boolean variable userexception will be true.
				 */
				boolean usernameexception = checkprevioususernames(username);
				boolean passwordexception = checkforequalpasswords(password,
						passwordagian1);
				boolean empltyexception = checkemptyinput(username, password);
				Integer age = 0;
				boolean ageexception = checkvalidage(age);
				String gender = "";
				boolean genderexception = checkgenderinput(gender);
				boolean lengthexception = checkinputlength(name, username,
						password);

				/**
				 * if userexceptions are false user data will be entered to the
				 * database.
				 */
				if (usernameexception == false && passwordexception == false
						&& empltyexception == false && ageexception == false
						&& genderexception == false && lengthexception == false) {
					Logindatabase ldb = new Logindatabase(getBaseContext());
					// insert details
					ldb.insertpatient(name, age, gender, username, password);
					
					//shows massege and goes back to loging
					message();
					
				}
			}
		});
	}

	public void message(){
		final AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setCancelable(true);
		build.setTitle("Sucess!");
		build.setMessage("Account Sucessfully created!");
		build.show();
		new CountDownTimer(1000, 100) {

		     public void onFinish() {
		    	 back.performClick();
		     }

			@Override
			public void onTick(long arg0) {
				
			}
		}.start();
		
	}
	// checks if input age is valid
	public boolean checkvalidage(int age) {
		boolean userexception = false;

		try {
			age = Integer.valueOf(Agein.getText().toString());
		} catch (NumberFormatException ex) {
			userexception = true;
		}

		if (!(age > 1)) {
			registernotice.setText("Please enter valid age!");
			userexception = true;
		}
		return userexception;
	}

	// checks if username is already taken
	public boolean checkprevioususernames(String username) {
		boolean yesprevious = false;
		Logindatabase ldb = new Logindatabase(getBaseContext());
		// hashmap for username and passwords
		HashMap uandp = new HashMap<String, String>();
		// assign all the usernames and passwords from database
		uandp = ldb.getdata();
		for (Object key : uandp.keySet()) {
			if (key.equals(username)) {
				yesprevious = true;
				registernotice.setText("Username is not available!");
			}
		}
		return yesprevious;
	}

	// checks if inputs are empty
	public boolean checkemptyinput(String username, String password) {
		boolean userexception = false;
		if (username.equals("") || password.equals("")) {
			registernotice.setText("please fill all fields");
			userexception = true;
		}
		return userexception;
	}

	// checks for user name length
	public boolean checkinputlength(String name, String username,
			String password) {
		boolean userexception = false;
		if (username.length() > 10) {
			userexception = true;
			registernotice.setText("Username is too long!");
		}
		if (password.length() > 10) {
			userexception = true;
			registernotice.setText("Password is too long!");
		}
		if (name.length() > 20) {
			userexception = true;
			registernotice.setText("name is too long!");
		}
		return userexception;
	}

	// checks errors in genderinput
	public boolean checkgenderinput(String gender) {
		boolean userexception = false;
		if (femalein.isChecked()) {
			gender = "female";
		}

		if (malein.isChecked()) {
			gender = "male";
		}
		if ((femalein.isChecked() && malein.isChecked())) {
			registernotice.setText("only tick one box");
			userexception = true;
		}
		if (!(femalein.isChecked() || malein.isChecked())) {
			registernotice.setText("please fill all fields");
			userexception = true;
		}

		return userexception;
	}

	// checks if password confirmation is correct
	public boolean checkforequalpasswords(String password, String passwordagian1) {
		boolean userexception = false;
		if (!(password.equals(passwordagian1))) {
			registernotice.setText("password does not match!");
			userexception = true;
		}
		return userexception;
	}

}