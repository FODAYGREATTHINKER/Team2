package com.example.medicalbookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Logindatabase extends SQLiteOpenHelper {
	
	private static String DBName="Login.db";
	private static String TableName="PatientRegister";
	public static String column_name="Name";
	//public static String column_age="Age";
	public static String column_username="Username";
	public static String column_password="Password";
	//public static String column_gender="Gender";
	

	public Logindatabase(Context context){
		super(context,DBName,null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//creates table
		db.execSQL("create table PatientRegister" + "(Name text,Username text,Password text)"
		);
		
	}

	
	//deletes and creates table if it exist
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		//db.execSQL("DROP TABLE IF EXIST PatientRegister");
		//onCreate(db);
	}
	
	public void insertpatient(String Name,String Username,String Password){
		
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put("NAME", Name);
		cv.put("Username", Username);
		cv.put("Password",Password);		
		db.insert("PatientRegister", null, cv);
		
	}
	
	public String getdata(){
		//geting first row and printing it
	    SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.rawQuery("SELECT * from PatientRegister", null);
		String count=Integer.toString(cursor.getColumnCount());
		return count;
		
	}
	

}
