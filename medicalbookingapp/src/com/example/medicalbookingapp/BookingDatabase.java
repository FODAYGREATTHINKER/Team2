package com.example.medicalbookingapp;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingDatabase extends SQLiteOpenHelper {
	private static String DBName2 = "Booking.db";
	private static String TableName = "Patient_Bookings";
	public static String column_ID = "Booking_ID";
	public static String columnUsername = "Customer_username";
	public static String columnMedicalcentre = "MedicalCentres";
	public static String columnDoctor = "DoctorName";
	public static String columnDate = "Booking_Date";
	public static String columnTime = "Booking_Time";

	public BookingDatabase(Context context) {
		super(context, DBName2, null, 1);
	}

	/* This method create the database */
	public void onCreate(SQLiteDatabase db) {
		// creates table
		db.execSQL("create table Patient_Bookings"
				+ "(Booking_ID integer primary key autoincrement not null,Customer_username text,MedicalCentres text, DoctorName text, Booking_Date text, Booking_Time Text)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST Patient_Bookings");
		onCreate(db);
	}

	/* Method that inserts the values into the bookings table in the database */
	public void insertBooking(String username, String medicalCentre,
			String doctorName, String bookingDate, String bookingTime) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put("Customer_username", username);
		content.put("MedicalCentres", medicalCentre);
		content.put("DoctorName", doctorName);
		content.put("Booking_Date", (bookingDate));
		content.put("Booking_Time", (bookingTime));
		db.insert("Patient_Bookings", null, content);
	}

	/* Method that gets the bookings made from the database */
	public Cursor getBookings(String usName) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor result = db.rawQuery("select * from " + TableName
				+ " where Customer_username LIKE '" + usName + "'", null);
		return result;

	}

}
