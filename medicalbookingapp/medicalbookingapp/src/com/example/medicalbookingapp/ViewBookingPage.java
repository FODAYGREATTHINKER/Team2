package com.example.medicalbookingapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class ViewBookingPage extends Activity {

	BookingDatabase viewBookings;
	private ListView lv;
	private ImageButton backBtn;
	private Cursor view;
	private static String bkNum;
	private String username;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_list);

		viewBookings = new BookingDatabase(this);
		username = Home.getusername();
		lv = (ListView) findViewById(R.id.listView1);
		backBtn = (ImageButton) findViewById(R.id.backBtn);
		view = viewBookings.getBookings(username);

		populateListView();

		backToHome();
		chosenBooking();

	}

	public void populateListView() {
		List<String> bkList = new ArrayList<String>();
		ArrayAdapter<String> arrayAdapter;

		if (view.getCount() == 0) {
			lv.setEmptyView(findViewById(R.id.empty_list_view));
		} else {
			while (view.moveToNext()) {
				bkList.add(view.getString(0) + ". Booking on: " + view.getString(4));
			}
			arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bkList);
			lv.setAdapter(arrayAdapter);
		}
	}

	public void backToHome() {
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewBookingPage.this, Home.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
	}

	public void chosenBooking() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				String result = (String) adapter.getItemAtPosition(position);
				bkNum = result.substring(0, result.indexOf('.'));

				Intent Intent = new Intent(ViewBookingPage.this, DetailedBooking.class);
				startActivity(Intent);

			}
		});
	}

	public static String getBkNum() {
		return bkNum;
	}

}
