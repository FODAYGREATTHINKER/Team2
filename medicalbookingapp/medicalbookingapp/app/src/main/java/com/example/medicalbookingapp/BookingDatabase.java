package com.example.medicalbookingapp;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingDatabase extends SQLiteOpenHelper{
	private static String DBName = "Booking.db";
	private static String TableName = "Patient_Bookings";
	public static String column_ID = "Booking_ID";
	public static String columnUsername = "Customer_username";
	public static String columnMedicalcentre = "MedicalCentres";
	public static String columnDoctor = "DoctorName";
	public static String columnDate = "Booking_Date";
	
	
	 public BookingDatabase(Context context){
	      super(context,DBName,null,1);
	   }

	 public void onCreate(SQLiteDatabase db) {
			// creates table
			db.execSQL("create table Patient_Bookings" +TableName 
					+ "(Booking_ID integer PRIMARY KEY Autoincrement,Customer_username text,MedicalCentres text, DoctorName text, Booking_Date text)");

		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	 
	public boolean insertBooking(String username, String medicalCentre, String doctorName, String bookingDate) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(columnMedicalcentre, medicalCentre);
		content.put(columnUsername, username);
		content.put(columnDoctor, doctorName);
		content.put(columnDate, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bookingDate));
		long result= db.insert(TableName, null, content);
		/*Check if values are really inserted to our Table or not
		*if result is equal to -1 then it means no new rows inserted in to our table 
		*/
		if(result == -1)
			return false;
		else
			return true;

	}
	
}

