package com.example.medicalbookingapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailedBooking extends Activity {
	public TextView bkid;
	public TextView medCent;
	public TextView doc;
	public TextView date;
	public TextView time;
	public Button cancel;
	public ImageButton back;
	BookingDatabase bookingRow;
	private String chosenBking;
	private int rowID;

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

	public void setupTextViews() {
		bkid = (TextView) findViewById(R.id.bkIDInfo);
		medCent = (TextView) findViewById(R.id.medCentInfo);
		doc = (TextView) findViewById(R.id.docInfo);
		date = (TextView) findViewById(R.id.dateInfo);
		time = (TextView) findViewById(R.id.timeInfo);
	}

	public void setDetails() {

		Cursor getRow = bookingRow.getBookingRow(rowID);

		bkid.setText(getRow.getString(0));
		medCent.setText(getRow.getString(2));
		doc.setText(getRow.getString(3));
		date.setText(getRow.getString(4));
		time.setText(getRow.getString(5));
	}

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