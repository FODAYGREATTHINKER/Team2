package com.example.medicalbookingapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Userpage extends Activity {
	TextView name, username, Age, Gender;
	TextView changetext, currentpassword, newpassword, passwordnotice;
	Button confirm,cancel, changepassword;
	String user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userpage);
		setupelements();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			user = extras.getString("username");

		}
		getuserdetails(user);
		changepassword();
	}

	public void setupelements() {
		name = (TextView) findViewById(R.id.nameid);
		username = (TextView) findViewById(R.id.usernameid);
		Age = (TextView) findViewById(R.id.ageid);
		Gender = (TextView) findViewById(R.id.genderid);
		passwordnotice = (TextView) findViewById(R.id.passwordnotice);

	}

	// contains method for changing password
	public void changepassword() {
		changepassword = (Button) findViewById(R.id.changepassword);
		changepassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				changetext = (TextView) findViewById(R.id.textchange);
				currentpassword = (EditText) findViewById(R.id.currentpassword);
				newpassword = (EditText) findViewById(R.id.newpassword);
				confirm = (Button) findViewById(R.id.passwordconfirm);
                cancel=(Button) findViewById(R.id.cancel);
                
				// set fields visible for password change
				setvisible();
				confirm.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						comparepasswords();
					}
				});
				cancel.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View arg0) {
						setinvisible();
						clearfields();
					}
					
				});
			}
			

		});
	}

	// this method checks if inputs are correct and notice accordingly
	public void comparepasswords() {
		String currentpas = String
				.valueOf(currentpassword.getText().toString());
		String newpas = String.valueOf(newpassword.getText().toString());
		
		boolean correct = false;
		if (currentpas.equals(getCurrentpassword())) {

			if (newpas.equals("")) {
				passwordnotice.setText("new password is empty!");
			} else {

				// insert new password to database
				updatedatabase(newpas);

				// reset elements
				clearfields();
				correct = true;
			}

		} else {

			passwordnotice.setText("Wrong current password!");
			if (currentpas.equals("")) {
				passwordnotice.setText("fill all fields");
			}

		}

		// after confirm click password input fields will be
		// invisible
		if (correct == true) {
			setinvisible();
		}

	}

	public void updatedatabase(String newpas) {
		Logindatabase ldb = new Logindatabase(getBaseContext());
		ldb.updatepassword(user, newpas);
		Toast.makeText(getApplicationContext(), "Password changed",
				Toast.LENGTH_LONG).show();
	}

	// set password inputs visible
	public void setvisible() {
		changepassword.setVisibility(View.INVISIBLE);
		changetext.setVisibility(View.VISIBLE);
		currentpassword.setVisibility(View.VISIBLE);
		newpassword.setVisibility(View.VISIBLE);
		confirm.setVisibility(View.VISIBLE);
		cancel.setVisibility(View.VISIBLE);
	}
    
	//clears all text in elements
	public void clearfields(){
		passwordnotice.setText("");
		currentpassword.setText("");
		newpassword.setText("");
	}
	// sets password inputs invisible
	public void setinvisible() {
		newpassword.setVisibility(View.INVISIBLE);
		changetext.setVisibility(View.INVISIBLE);
		currentpassword.setVisibility(View.INVISIBLE);
		newpassword.setVisibility(View.INVISIBLE);
		confirm.setVisibility(View.INVISIBLE);
		cancel.setVisibility(View.INVISIBLE);
		changepassword.setVisibility(View.VISIBLE);
	}

	// gets current password from database
	public String getCurrentpassword() {
		String pass = "";
		Logindatabase ldb = new Logindatabase(getBaseContext());
		Cursor view = ldb.Getuserdetails(user);
		while (view.moveToNext()) {
			pass = (view.getString(4));
		}
		return pass;
	}

	// get user data from the database
	public void getuserdetails(String user) {
		Logindatabase ldb = new Logindatabase(getBaseContext());
		Cursor view = ldb.Getuserdetails(user);
		while (view.moveToNext()) {
			name.setText(view.getString(0));
			username.setText(view.getString(3));
			Gender.setText(view.getString(2));
			Age.setText(view.getString(1));
			
		}
	}

}
