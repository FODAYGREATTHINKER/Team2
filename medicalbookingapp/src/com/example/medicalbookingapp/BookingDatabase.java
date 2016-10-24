package com.example.medicalbookingapp;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingDatabase extends SQLiteOpenHelper {
	public static String DBName2 = "Booking.db";
	public static final String tableName = "Patient_Bookings";
	public static final String columnID = "Booking_ID";
	public static final String columnUsername = "Customer_username";
	public static final String columnMedicalcentre = "MedicalCentres";
	public static final String columnDoctor = "DoctorName";
	public static final String columnDate = "Booking_Date";
	public static final String columnTime = "Booking_Time";

	public static final String[] ALL_KEYS = new String[] { columnID, columnUsername, columnMedicalcentre, columnDoctor,
			columnDate, columnTime };

	SQLiteDatabase db = this.getReadableDatabase();

	public BookingDatabase(Context context) {
		super(context, DBName2, null, 1);
	}

	/* This method create the database */
	public void onCreate(SQLiteDatabase db) {
		// creates table
		db.execSQL("create table " + tableName + " (" + columnID + " integer primary key autoincrement, "
				+ columnUsername + " text, " + columnMedicalcentre + " text, " + columnDoctor
				+ " text, " + columnDate + " text, " + columnTime + " string" + ");");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST "+tableName);
		onCreate(db);
	}

	/* Method that inserts the values into the bookings table in the database */
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

	/* Method that gets the bookings made from the database */
	public Cursor getBookings(String usName) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor result = db.rawQuery("select * from " + tableName + " where Customer_username LIKE '" + usName + "'",
				null);
		return result;

	}
	
	public Cursor getCurrentBookings(String usName) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor result = db.rawQuery("select * from " + tableName + " where Customer_username LIKE '" + usName
				+ "' AND Booking_Date >= date('now')", null);

		return result;
}

	public Cursor getBookingRow(int id) {
		String where = columnID + "=" + id;
		Cursor c = db.query(true, tableName, ALL_KEYS, where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public boolean deleteRow(long rowId) {
		String where = columnID + "=" + rowId;
		return db.delete(tableName, where, null) != 0;
	}

}
