package com.example.medicalbookingapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

/* Home class with buttons that take the user to other functionalities of the application.  */
public class Home extends Activity {
	static// variable declaration from user welcome text
	String name;
	TextView wname;
	BookingDatabase bd;
	String value;

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

	/* Method that setups button functionality on the hom page */
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

	/* this method can be used for getting username for other methods */
	public static String getusername() {
		return name;
	}

	public boolean notfuture(String day) {
		boolean notin = false;
		SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		Date dates;
		try {
			dates = fm.parse(day);
			if (new Date().after(dates)) {
				notin = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return notin;
	}

	/* Method that views the bookings for the user. */
	public void view(boolean future) {
		Cursor view = bd.getBookings(name);
		if (view.getCount() == 0) {
			showBookings("No Bookings", "You have not made a booking yet!");
			return;
		}
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		while (view.moveToNext()) {

			if ((future == true && (!notfuture(view.getString(4))))) {

				buffer.append("Id: " + view.getString(0) + "\n");
				buffer.append("Username: " + view.getString(1) + "\n");
				buffer.append("MedicalCentres: " + view.getString(2) + "\n");
				buffer.append("Doctor: " + view.getString(3) + "\n");
				buffer.append("BookingDate: " + view.getString(4) + "\n");
				buffer.append("BookingTime: " + view.getString(5) + "\n");
				count++;
			}

		}
		if (count > 0) {
			showBookings("Bookings", buffer.toString());
		}

		if (count == 0) {

			showBookings("No Bookings", "You have no future bookings!");
		}
	}

	/*
	 * Method that builds an alert dialog that shows the bookings to the user.
	 */
	public void showBookings(String message, String msg) {
		AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setCancelable(true);
		build.setTitle(message);
		build.setMessage(msg);
		build.show();
	}
}