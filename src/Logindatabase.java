package com.example.medicalbookingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Logindatabase extends SQLiteOpenHelper {
	
	private String DBName="Login.db";
	private String TableName="PatientRegister";
	public String column_id="Id";
	public String column_name="Name";
	public String column_age="age";
	public String column_patientid="Password";
	

	public Logindatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
