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

/**
 * Page for making bookings
 *
 */
public class BookingPage extends Activity implements OnItemSelectedListener {
	private Spinner medicalCent, doc, timeChosen;
	private BookingDatabase bd;
	private String name; // user's user name
	ArrayAdapter<CharSequence> specificDoc; // to change data based on medical
											// center chosen.
	private TextView error;
	private DatePicker dates;

	/**
	 * When the page is opened, this method creates the functions / methods for
	 * the booking page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_page);
		name = Home.getusername();
		bd = new BookingDatabase(getBaseContext());
		createSpinners();
		BackPage();
		final DatePicker dp = (DatePicker) findViewById(R.id.bookDate);
		dp.setCalendarViewShown(false);
		getDate();
		confirm();
		// viewMap();

	}

	/**
	 * Creates the three spinners and inserts data from arrays.
	 */
	public void createSpinners() {
		medicalCent = (Spinner) findViewById(R.id.spinner1);
		// outputs message when something is selected.
		medicalCent.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.medical_center,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		medicalCent.setAdapter(adapter);
		medicalCent.setPrompt("Select Medical Center");
		doc = (Spinner) findViewById(R.id.spinner2);
		doc.setOnItemSelectedListener(null);
		timeChosen = (Spinner) findViewById(R.id.spinner3);
		// outputs message when something is selected.
		timeChosen.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> timeArray = ArrayAdapter.createFromResource(this, R.array.Time,
				android.R.layout.simple_spinner_item);
		timeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Throw in that next level for loop.
		timeChosen.setAdapter(timeArray);
		timeChosen.setPrompt("Select Time");
	}

	/*
	 * this method changes the dropdown lists depending on which medical centre
	 * is chosen. Changes doctor dropdown list depending on medical centre.
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (medicalCent.getSelectedItem().equals("Mubas Medical")) {
			specificDoc = ArrayAdapter.createFromResource(this, R.array.Doctors1, android.R.layout.simple_spinner_item);
			specificDoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			doc.setAdapter(specificDoc);
			doc.setPrompt("Select Doctor");
		} else if (medicalCent.getSelectedItem().equals("Shortland Street")) {
			specificDoc = ArrayAdapter.createFromResource(this, R.array.Doctors2, android.R.layout.simple_spinner_item);
			specificDoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			doc.setAdapter(specificDoc);
			doc.setPrompt("Select Doctor");
		}
	}

	/**
	 * Gets the date from the date picker
	 * 
	 * @return the dates chosen in string format
	 */
	public String getDate() {
		dates = (DatePicker) findViewById(R.id.bookDate);
		int day = dates.getDayOfMonth();
		int month = dates.getMonth() + 1;
		int year = dates.getYear();

		return year + "-" + month + "-" + day;
	}

	/**
	 * gets the information from application and stores it in booking database.
	 */
	public void confirm() {
		Button b = (Button) findViewById(R.id.back1);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// get chosen data from spinner and puts in string values:
				medicalCent = (Spinner) findViewById(R.id.spinner1);
				doc = (Spinner) findViewById(R.id.spinner2);
				timeChosen = (Spinner) findViewById(R.id.spinner3);
				dates = (DatePicker) findViewById(R.id.bookDate);
				String med = medicalCent.getSelectedItem().toString();
				String doctor = doc.getSelectedItem().toString();
				String time = timeChosen.getSelectedItem().toString();
				String date = getDate().toString();
				// checks to see if all details are correct
				if (checkTime("Patient_Bookings", "MedicalCentres", med, "DoctorName", doctor, "Booking_Date",
						getDate(), "Booking_Time", time) == true && datePast() == true
						&& isDateWithin3Months(getDate().toString(), "yyyy-MM-dd") == true) {
					bd.insertBooking(name, med, doctor, date, time);
					bd.close();
					finish(); // returns to home screen
					Toast.makeText(getApplicationContext(), "Booking confirmed", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * Checks to see if date is within 3 months.
	 * 
	 * @param dateToValidate
	 *            the date to be checked
	 * @param dateFormat
	 *            the correct format to check the date with.
	 * @return true if date is fine or false if incorrect
	 */
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
	/**
	 * Checks to see if date booked is a previous date If date is previous date
	 * 
	 * @return true if everything is fine or false if date is already taken
	 */
	public boolean datePast() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dates = fm.parse(getDate().toString());
			 if(new Date().equals(dates)){
					return true;
				}
			 else if (new Date().after(dates)) {
				error.setText("Booking only can made from tommorow");
				return false;
			}
			
			return true;
		
				
			
		} catch (ParseException e) {
			error.setText("Wrong format for date");
			return false;
		}
	}

	/**
	 * checks database to make sure date and time are not similar for doctors of
	 * the medical center
	 * 
	 * @param tableName
	 *            table to check
	 * @param med
	 *            medical center column
	 * @param medField
	 *            medical center that has been chosen
	 * @param Doc
	 *            doctor column
	 * @param docField
	 *            doctor that has been chosen
	 * @param Booking
	 *            date column
	 * @param BookField
	 *            date that has been chosen
	 * @param Time
	 *            column
	 * @param timeField
	 *            time that has been chosen
	 * @return true if information in database does not exist
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

	/** This method creates a link from Booking Page back to the Home Page. */
	public void BackPage() {
		ImageButton b = (ImageButton) findViewById(R.id.button3);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * Was supposed to be link to maps page, but couldn't finish it.
	 */
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