package com.example.medicalbookingapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;

//Page for making bookings.
public class BookingPage extends Activity implements OnItemSelectedListener {
	private Spinner spinner, spinner2, spinner3;
	private Button btn;
	Button b;
	String text;
	BookingDatabase bd;
	String name = Home.getusername();
	ArrayAdapter<CharSequence> adapter1;
	TextView error;
	DatePicker dates;

	/*
	 * When the page is opened, this method creates the functions / methods for
	 * the booking page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_page);
		bd = new BookingDatabase(getBaseContext());
		createSpinners();
		BackPage();
		final DatePicker dp = (DatePicker) findViewById(R.id.bookDate);
		dp.setCalendarViewShown(false);
		getDate();
		confirm();
		// viewMap();

	}

	/* Creates the three spinners and inserts data from array. */
	public void createSpinners() {
		spinner = (Spinner) findViewById(R.id.spinner1);
		// outputs message when something is selected.
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.medical_center,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setPrompt("Select Medical Center");
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(null);
		Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
		// outputs message when something is selected.
		spinner3.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Time,
				android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Throw in that next level for loop.
		spinner3.setAdapter(adapter2);
		spinner3.setPrompt("Select Time");
	}

	/*
	 * this method changes the dropdown lists depending on which medical centre
	 * is chosen. Changes doctor dropdown list depending on medical centre.
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (spinner.getSelectedItem().equals("Mubas Medical")) {
			adapter1 = ArrayAdapter.createFromResource(this, R.array.Doctors1, android.R.layout.simple_spinner_item);
			adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(adapter1);
			spinner2.setPrompt("Select Doctor");
		} else if (spinner.getSelectedItem().equals("Shortland Street")) {
			adapter1 = ArrayAdapter.createFromResource(this, R.array.Doctors2, android.R.layout.simple_spinner_item);
			adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(adapter1);
			spinner2.setPrompt("Select Doctor");
		}
	}

	// Gets the date values
	public String getDate() {
		dates = (DatePicker) findViewById(R.id.bookDate);
		int day = dates.getDayOfMonth();
		int month = dates.getMonth() + 1;
		int year = dates.getYear();

		return year + "-" + month + "-" + day;
	}

	/*
	 * gets the information from application and stores it in booking database.
	 */
	// checks for errors.
	public void confirm() {
		Button b = (Button) findViewById(R.id.back1);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				spinner = (Spinner) findViewById(R.id.spinner1);
				spinner2 = (Spinner) findViewById(R.id.spinner2);
				spinner3 = (Spinner) findViewById(R.id.spinner3);
				dates = (DatePicker) findViewById(R.id.bookDate);
				String med = spinner.getSelectedItem().toString();
				String doctor = spinner2.getSelectedItem().toString();
				String time = spinner3.getSelectedItem().toString();
				String date = getDate().toString();
				if ( checkTime("Patient_Bookings", "MedicalCentres", med, "DoctorName", doctor, "Booking_Date",
						getDate(), "Booking_Time", time) == true  && datePast()==true
						&& isDateWithin3Months(getDate().toString(), "yyyy-MM-dd") == true) {
					bd.insertBooking(name, med, doctor, date, time);
					bd.close();
					finish();
					Toast.makeText(getApplicationContext(), "Booking confirmed", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	// Checks to see if date is within 3 months.

	public boolean isDateWithin3Months(String dateToValidate, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(dateToValidate);
			Calendar currentDateAfter3Months = Calendar.getInstance();
			currentDateAfter3Months.add(Calendar.MONTH, 3);

			// current date before 3 months
			Calendar currentDateBefore3Months = Calendar.getInstance();
			currentDateBefore3Months.add(Calendar.MONTH, -3);

			if (date.before(currentDateAfter3Months.getTime()) && date.after(currentDateBefore3Months.getTime())) {

				// ok everything is fine, date in range
				return true;
			} else {
				error.setText("Book only within 3 months");
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	/*
	 * Checks to see if date booked is a previous date If date is previous date
	 * output error.
	 */

	public boolean datePast(){
    	SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
    	try{
    		Date dates = fm.parse(getDate().toString());
    		if(new Date().after(dates)){
    			error.setText("Book only from tomorrow onwards.");
    			return false;
    		}
    		return true;
    	}
    	catch(ParseException e){
    		error.setText("Wrong format for date");
    		return false;
    }
    }

	/*
	 * checks database to make sure date and time are not similar for doctors of
	 * the medical centre. returns true if information in database does not
	 * exist.
	 */
	public boolean checkTime(String tableName, String med, String medField, String Doc, String docField, String Booking,
			String BookField, String Time, String timeField) {
		error = (TextView) findViewById(R.id.welcHeader);
		BookingDatabase helper = new BookingDatabase(this);
		SQLiteDatabase bd = helper.getReadableDatabase();

		String Query = "Select * from " + tableName + " where " + med + " LIKE '%" + medField + "%' AND " + Doc
				+ " LIKE '%" + docField + "%' AND " + Booking + " LIKE '%" + BookField + "%' AND " + Time + " LIKE '%"
				+ timeField + "%'";
		;
		Cursor c = bd.rawQuery(Query, null);
		if ((c.getCount() <= 0)) {

			c.close();
			return true;
		}
		error.setText("Time already booked.");
		c.close();
		return false;
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	// This method creates a link from Booking Page back to the Home Page.
	public void BackPage() {
		ImageButton b = (ImageButton) findViewById(R.id.button3);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/*
	 * public void viewMap() { Button b = (Button) findViewById(R.id.button4);
	 * b.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { Intent intent = new
	 * Intent(BookingPage.this, MapsActivity.class); startActivity(intent); }
	 * }); }
	 */
	/* Signs out of the application if users doesn't want to make a booking. */
}