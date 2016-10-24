package com.example.medicalbookingapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class History extends Activity {

	private ImageButton back;
	private Button history, delete;
	private BookingDatabase bd;

	private String user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			user = extras.getString("username");
		}
		
		//creates elements 
		setupelements();
		
	}
	
	//create elements and instantiate them
	public void setupelements(){
		history = (Button) findViewById(R.id.history);
		delete = (Button) findViewById(R.id.delete);
		back = (ImageButton) findViewById(R.id.histBackBtn);
		bd = new BookingDatabase(this);

		history.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				view(user);

			}
		});

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				deletehistory(user);

			}
		});

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(History.this, Home.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);

			}
		});

	}
 
	//checks if the date in the future
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

	//shows bookings
	public void view(String name) {
		Cursor view = bd.getBookings(name);
		if (view.getCount() == 0) {
			buffer("No Bookings", "You have not made a booking yet!");
			return;
		}
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		while (view.moveToNext()) {

			if (notfuture(view.getString(4))) {

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
			buffer("Bookings", buffer.toString());
		}

		if (count == 0) {

			buffer("No Bookings", "You have no past booking!");
		}

	}

	// deletes history
	public void deletehistory(String name) {
		Cursor view = bd.getBookings(name);
		int count = view.getCount();
		if (view.getCount() == 0) {

			buffer("No Bookings", "You dont have any booking !");
			return;
		} else {

			BookingDatabase helper = new BookingDatabase(this);
			SQLiteDatabase bd = helper.getWritableDatabase();
			String Query = "Delete from  Patient_Bookings  where Booking_Date < strftime('%Y-%m-%d', 'now') AND Customer_username like '%"+name+"%'";
			bd.execSQL(Query);
			if (view.getCount() < count) {
				buffer("Past bookings deleted", "Past bookings deleted!");
			} else {
				buffer("No past bookings", "You dont have any past booking to delete!");
			}

		}
	}

	// prints the bookings
	public void buffer(String message, String msg) {
		AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setCancelable(true);
		build.setTitle(message);
		build.setMessage(msg);
		build.show();
	}
}
