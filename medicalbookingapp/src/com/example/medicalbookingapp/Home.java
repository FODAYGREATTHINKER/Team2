package com.example.medicalbookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home class with buttons that take the user to other functionalities of the
 * application.
 */
public class Home extends Activity {
	static String name; // variable declaration from user welcome text
	private TextView wname; // welcome name
	private BookingDatabase bd;
	private String value; // to store username for further use

	/**
	 * Sets ups all elements on page.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		wname = (TextView) findViewById(R.id.wusername);
		bd = new BookingDatabase(this);
		// gets username with intent from main when login sucessfull
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			value = extras.getString("username");
			wname.setText(value);
			name = value;
		}
		setupButtons();
	}

	/** Method that setups button functionality on the hom page */
	public void setupButtons() {
		// Button that links home page with booking page.
		Button makeBooking = (Button) findViewById(R.id.makeBkingBtn);
		makeBooking.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent Intent = new Intent(Home.this, BookingPage.class);
				startActivity(Intent);
			}
		});
		Button view = (Button) findViewById(R.id.viewBtn);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this, ViewBookingPage.class);
				startActivity(intent);
			}
		});

		Button history = (Button) findViewById(R.id.history);
		history.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent Intent = new Intent(Home.this, History.class);
				Intent.putExtra("username", name);
				startActivity(Intent);
			}
		});

		Button logout = (Button) findViewById(R.id.logoutBtn);
		logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		Button med = (Button) findViewById(R.id.medical);
		med.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this, MedDetails.class);
				startActivity(intent);
			}
		});
		// sprint2 add user button
		ImageButton profile = (ImageButton) findViewById(R.id.profile);
		profile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// goes to user page
				Intent intent = new Intent(getApplicationContext(), Userpage.class);
				intent.putExtra("username", name);
				startActivity(intent);
			}
		});
	}

	/**
	 * this method can be used for getting username for other classes / pages
	 */
	public static String getusername() {
		return name;
	}

}