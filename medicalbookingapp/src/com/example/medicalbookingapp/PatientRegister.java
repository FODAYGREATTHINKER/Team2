package com.example.medicalbookingapp;

import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Page for signing up for a new account in order to use the app.
 *
 */
public class PatientRegister extends Activity {
	// variable declarations
	private Button register;
	private ImageButton back;
	private EditText namein, usernamein, passwordin, passwordagian, Agein, medicalconditoin;
	private TextView textview2, registernotice;
	private CheckBox femalein, malein;

	/**
	 * Sets up the page
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patientregister);
		// back button
		back = (ImageButton) findViewById(R.id.back);
		// goes back to login screen
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		setupelements();
		// register when register button pressed and gets data from elements
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// assigns variables with user entered values
				String username = String.valueOf(usernamein.getText().toString());
				String password = String.valueOf(passwordin.getText().toString());
				String passwordagian1 = String.valueOf(passwordagian.getText().toString());
				String name = String.valueOf(namein.getText().toString());
				String medicalcondition = String.valueOf(medicalconditoin.getText().toString());
				// boolean variable if user has wrong inputs or empty inputs.
				/**
				 * checks userinput with the methods. if inputs are wrong
				 * boolean variable userexception will be true.
				 */
				boolean usernameexception = checkprevioususernames(username);
				boolean passwordexception = checkforequalpasswords(password, passwordagian1);
				boolean empltyexception = checkemptyinput(username, password);
				// Integer age = 18;
				boolean ageexception = checkvalidage();
				String gender = "";
				boolean genderexception = checkgenderinput(gender);
				boolean lengthexception = checkinputlength(name, username, password);
				boolean condionexception = checkconditin(medicalcondition);

				/**
				 * if userexceptions are false user data will be entered to the
				 * database.
				 */
				if (usernameexception == false && passwordexception == false && empltyexception == false
						&& ageexception == false && genderexception == false && lengthexception == false
						&& condionexception == false) {
					Logindatabase ldb = new Logindatabase(getBaseContext());
					// insert details

					if (femalein.isChecked()) {
						gender = "female";
					}
					if (malein.isChecked()) {
						gender = "male";
					}
					Integer age = Ageconverter();
					ldb.insertpatient(name, gender, age, username, password, medicalcondition);

					// shows massege and goes back to loging
					message();

				}
			}
		});
	}

	/**
	 * Method the references all elements on xml file
	 */
	public void setupelements() {
		register = (Button) findViewById(R.id.Signup);
		namein = (EditText) findViewById(R.id.namein);
		usernamein = (EditText) findViewById(R.id.usernamein);
		passwordin = (EditText) findViewById(R.id.passwordin);
		passwordagian = (EditText) findViewById(R.id.passwordagain);
		Agein = (EditText) findViewById(R.id.currentpassword);
		femalein = (CheckBox) findViewById(R.id.checkBox2);
		malein = (CheckBox) findViewById(R.id.checkBox1);
		registernotice = (TextView) findViewById(R.id.registernotice);
		medicalconditoin = (EditText) findViewById(R.id.medicalcondition);
	}

	/** shows message when account created */
	public void message() {
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

	/** convert age */
	public Integer Ageconverter() {

		Integer age = Integer.valueOf(Agein.getText().toString());
		return age;

	}

	/**
	 * Method to check the condition length
	 * 
	 * @param condition
	 *            the condition to check
	 * @return true if condition entered is too long.
	 */
	public boolean checkconditin(String condition) {
		boolean userexception = false;
		condition = String.valueOf(medicalconditoin.getText().toString());
		if (condition.length() > 6) {
			userexception = true;
			registernotice.setText("only enter condition with less than 6 letters ");
		}

		return userexception;
	}

	/**
	 * checks if input age is valid
	 * 
	 * @return true if age is valid.
	 */
	public boolean checkvalidage() {
		boolean userexception = false;
		int age = 0;
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

	/**
	 * checks if username is already taken
	 * 
	 * @param username
	 *            to check in database
	 * @return true if the username is already in use.
	 */
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

	/**
	 * checks if inputs are empty
	 * 
	 * @param username
	 *            field on page
	 * @param password
	 *            field on page
	 * @return true if user has not entered username or password.
	 */
	public boolean checkemptyinput(String username, String password) {
		boolean userexception = false;
		if (username.equals("") || password.equals("")) {
			registernotice.setText("please fill all fields");
			userexception = true;
		}
		return userexception;
	}

	/**
	 * checks for user name length
	 * 
	 * @param name
	 *            of user
	 * @param username
	 *            to check if it is appropriate length
	 * @param password
	 *            to check if it is appropriate length
	 * @return true if eithier username or passwor too long.
	 */
	public boolean checkinputlength(String name, String username, String password) {
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

	/**
	 * checks errors in gender input
	 * 
	 * @param gender
	 *            variable to check
	 * @return true if user has not checked in gender detail properly
	 */
	public boolean checkgenderinput(String gender) {
		boolean userexception = false;

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

	/**
	 * checks if password confirmation is correct
	 * 
	 * @param password
	 *            field on page
	 * @param passwordagian
	 *            field on page
	 * @return true if passwords do not match.
	 */
	public boolean checkforequalpasswords(String password, String passwordagian) {
		boolean userexception = false;
		if (!(password.equals(passwordagian))) {
			registernotice.setText("password does not match!");
			userexception = true;
		}
		return userexception;
	}
}