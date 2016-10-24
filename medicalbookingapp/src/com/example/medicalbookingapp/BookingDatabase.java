package com.example.medicalbookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * Database class for storing medical bookings.
 *
 */

public class BookingDatabase extends SQLiteOpenHelper {

	// Variables for database name, table and columns
	public static String DBName2 = "Booking.db";
	public static final String tableName = "Patient_Bookings";
	public static final String columnID = "Booking_ID";
	public static final String columnUsername = "Customer_username";
	public static final String columnMedicalcentre = "MedicalCentres";
	public static final String columnDoctor = "DoctorName";
	public static final String columnDate = "Booking_Date";
	public static final String columnTime = "Booking_Time";
	// String array to that stores all rows of table.
	public static final String[] ALL_Rows = new String[] { columnID, columnUsername, columnMedicalcentre, columnDoctor,
			columnDate, columnTime };

	SQLiteDatabase db = this.getReadableDatabase();

	// Constructor
	public BookingDatabase(Context context) {
		super(context, DBName2, null, 1);
	}

	/**
	 * This method create the database
	 */
	public void onCreate(SQLiteDatabase db) {
		// creates table
		db.execSQL("create table " + tableName + " (" + columnID + " integer primary key autoincrement, "
				+ columnUsername + " text, " + columnMedicalcentre + " text, " + columnDoctor + " text, " + columnDate
				+ " text, " + columnTime + " string" + ");");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST " + tableName);
		onCreate(db);
	}

	/**
	 * Method that inserts data into database table
	 * 
	 * @param username
	 *            the username of app user
	 * @param medicalCentre
	 *            name of center booked at.
	 * @param doctorName
	 *            name of doctor in booking
	 * @param bookingDate
	 *            date of booking.
	 * @param bookingTime
	 *            time of booking.
	 */
	public void insertBooking(String username, String medicalCentre, String doctorName, String bookingDate,
			String bookingTime) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(columnUsername, username);
		content.put(columnMedicalcentre, medicalCentre);
		content.put(columnDoctor, doctorName);
		content.put(columnDate, (bookingDate));
		content.put(columnTime, (bookingTime));
		db.insert(tableName, null, content);
	}

	/**
	 * Method that gets all the bookings of a user
	 * 
	 * @param usName
	 *            to search for bookings according to app user's username
	 * @return the cursor to the bookings made
	 */
	public Cursor getBookings(String usName) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor result = db.rawQuery("select * from " + tableName + " where Customer_username LIKE '" + usName + "'",
				null);
		return result;

	}

	/**
	 * Method that gets future bookings of a user
	 * 
	 * @param usName
	 *            to search for bookings according to app user's username
	 * @return the cursor to the bookings made
	 */
	public Cursor getCurrentBookings(String usName) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor result = db.rawQuery("select * from " + tableName + " where Customer_username LIKE '" + usName
				+ "' AND Booking_Date >= date('now')", null);

		return result;
	}

	/**
	 * To get a specific row so all details of booking can be shown.
	 * 
	 * @param id
	 *            of the booking chosen by user
	 * @return the specific row chosen.
	 */
	public Cursor getBookingRow(int id) {
		String where = columnID + "=" + id;
		Cursor c = db.query(true, tableName, ALL_Rows, where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	/**
	 * Method to delete a row
	 * 
	 * @param rowId
	 *            id of the specific row to delete.
	 * @return the row that is deleted.
	 */
	public boolean deleteRow(long rowId) {
		String where = columnID + "=" + rowId;
		return db.delete(tableName, where, null) != 0;
	}

}
