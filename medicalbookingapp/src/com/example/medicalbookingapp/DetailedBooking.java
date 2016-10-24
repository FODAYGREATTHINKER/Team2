package com.example.medicalbookingapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Page to view a detailed booking from the view booking page
 *
 */
public class DetailedBooking extends Activity {
	public TextView bkid;
	public TextView medCent;
	public TextView doc;
	public TextView date;
	public TextView time;
	public Button cancel;
	public ImageButton back;
	private BookingDatabase bookingRow;
	private String chosenBking;
	private int rowID;

	/**
	 * Sets up elements on page.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed_booking);

		chosenBking = ViewBookingPage.getBkNum();
		bookingRow = new BookingDatabase(this);
		rowID = Integer.parseInt(chosenBking);
		setupTextViews();
		setDetails();
		cancelBk();
		back();

	}

	/**
	 * Sets up textviews by finding reference in xml
	 */
	public void setupTextViews() {
		bkid = (TextView) findViewById(R.id.bkIDInfo);
		medCent = (TextView) findViewById(R.id.medCentInfo);
		doc = (TextView) findViewById(R.id.docInfo);
		date = (TextView) findViewById(R.id.dateInfo);
		time = (TextView) findViewById(R.id.timeInfo);
	}

	/**
	 * adds booking details to textview by getting specific row
	 */
	public void setDetails() {

		Cursor getRow = bookingRow.getBookingRow(rowID);

		bkid.setText(getRow.getString(0));
		medCent.setText(getRow.getString(2));
		doc.setText(getRow.getString(3));
		date.setText(getRow.getString(4));
		time.setText(getRow.getString(5));
	}

	/**
	 * removes booking from database when button is pressed
	 */
	public void cancelBk() {
		cancel = (Button) findViewById(R.id.btnCancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean remove = bookingRow.deleteRow(rowID);
				Intent Intent = new Intent(DetailedBooking.this, ViewBookingPage.class);
				startActivity(Intent);
			}
		});
	}

	/**
	 * takes user back to view bookings page.
	 */
	public void back() {
		back = (ImageButton) findViewById(R.id.detailBackBtn);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}