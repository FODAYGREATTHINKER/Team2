package com.example.medicalbookingapp;

import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database to store user information
 *
 */
public class Logindatabase extends SQLiteOpenHelper {
	// Database name, table name and column information:
	private static String DBName = "Login.db";
	private static String TableName = "PatientRegister";
	public static String column_name = "Name";
	public static String column_gender = "Gender";
	public static String column_age = "Age";
	public static String column_condition = "Condition";
	public static String column_username = "Username";
	public static String column_password = "Password";

	public Logindatabase(Context context) {
		super(context, DBName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creates table
		db.execSQL("create table PatientRegister"
				+ "(Name text,Age integer,Condition text,Gender text,Username text primary key,Password text)");
	}

	/** deletes and creates table if it exist */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST PatientRegister");
		onCreate(db);
	}

	/**
	 * Inserts user information into database
	 * 
	 * @param Name
	 *            of user
	 * @param Gender
	 *            of the user
	 * @param Age
	 *            of the user
	 * @param Username
	 *            that has been entered
	 * @param Password
	 *            that has been entered
	 * @param condition
	 *            if they user has any medical conditions.
	 */
	public void insertpatient(String Name, String Gender, Integer Age, String Username, String Password,
			String condition) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("Name", Name);
		cv.put("Gender", Gender);
		cv.put("Age", Age);
		cv.put("Username", Username);
		cv.put("Password", Password);
		cv.put("Condition", condition);
		db.insert("PatientRegister", null, cv);
	}

	/**
	 * Gets all the Usernames and Password from the database and return them as
	 * a Hashmap
	 * 
	 * @return returns all the data with Hashmap
	 */
	public HashMap getdata() {
		// geting first row and printing it
		SQLiteDatabase db = this.getReadableDatabase();
		// hashmap is for storing data to return
		HashMap hm = new HashMap();
		Cursor cursor = db.rawQuery("SELECT * from PatientRegister", null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {

			// String password = cursor.getString(3);
			hm.put(cursor.getString(cursor.getColumnIndex(column_username)),
					cursor.getString(cursor.getColumnIndex(column_password)));
			cursor.moveToNext();
		}
		return hm;
	}

	/**
	 * This method will get user name and password and updates password using
	 * querry
	 * 
	 * @param user
	 * @param pass
	 */
	public void updatepassword(String user, String pass) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE PatientRegister SET Password='" + pass + "' WHERE Username='" + user + "'");
	}

	/**
	 * This method will return cursor with all userdetails
	 */
	public Cursor Getuserdetails(String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor result = db.rawQuery("select * from " + TableName + " where Username LIKE '" + username + "'", null);
		return result;
	}
}