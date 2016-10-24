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

/**
 * Page to view current bookings of the user.
 *
 */
public class ViewBookingPage extends Activity {

	private BookingDatabase viewBookings;
	private ListView lv;
	private ImageButton backBtn;
	private Cursor view;
	private static String bkNum;
	private String username;

	/**
	 * Creates the elements on view bookings page.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_list);

		viewBookings = new BookingDatabase(this);
		username = Home.getusername();
		lv = (ListView) findViewById(R.id.listView1);
		backBtn = (ImageButton) findViewById(R.id.backBtn);
		view = viewBookings.getCurrentBookings(username);

		populateListView();

		backToHome();
		chosenBooking();

	}

	/**
	 * Populates a list view of all current bookings of user.
	 */
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

	/**
	 * back button that sends user back to home page.
	 */
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

	/**
	 * Method to send user to detailed bookings page when clicked on a certain
	 * booking.
	 */
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

	/**
	 * to get the booking number chosen when clicked on certain booking.
	 * 
	 * @return the booking id
	 */
	public static String getBkNum() {
		return bkNum;
	}

}
