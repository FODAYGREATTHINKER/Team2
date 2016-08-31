package com.example.medicalbookingapp;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	//components reference
	Button Register, login;
	EditText usernamein, passwordin;
	TextView notice;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Register = (Button) findViewById(R.id.Signup);
		login = (Button) findViewById(R.id.login);

		Register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//links patients register and starts the activity
				Intent Intent = new Intent(MainActivity.this, PatientRegister.class);
				startActivity(Intent);
			}
		});

		/*
		 * gets login details from filled textfields 
		 * 
		 */
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				usernamein = (EditText) findViewById(R.id.logusernamein);
				passwordin = (EditText) findViewById(R.id.logpasswordin);
				notice = (TextView) findViewById(R.id.notice);

				String username = String.valueOf(usernamein.getText()
						.toString());
				String password = String.valueOf(passwordin.getText()
						.toString());

				// creates a new object to get access to the database
				Logindatabase ldb = new Logindatabase(getBaseContext());
				// hashmap for username and passwords
				HashMap uandp = new HashMap();
				// assign all the usernames and passwords from database
				uandp = ldb.getdata();

				for (Object key : uandp.keySet()) {

					if (key.equals(username)) {

						Intent Intent = new Intent(MainActivity.this,Home.class);
						Intent.putExtra("username",username );
						startActivity(Intent);

					} else {

					}

				}
				shownotice();

			}
			
		});

	}
	
	
	
	
	public void shownotice(){
		
		notice.setText("Wrong details,if don't have account Signup");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
