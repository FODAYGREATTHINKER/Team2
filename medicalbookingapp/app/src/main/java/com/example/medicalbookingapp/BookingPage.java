package com.example.medicalbookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View.OnClickListener;

//Page for making bookings.
public class BookingPage extends Activity {
	private Spinner spinner1, spinner2;
	private Button btn;
	Button b;
	
	
	//When the page is opened.	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_page);
		createSpinners();
		addListenerOnButton();
		BackPage();
		SignOut();
	}
	
	//Creates the three spinners and inserts data from the array.
	public void createSpinners(){	
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.medical_center, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setPrompt("Select Medical Center");
	
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Doctors, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter1);
		spinner2.setPrompt("Select Doctor");
		
		Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Time, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Throw in that next level for loop.
		
		spinner3.setAdapter(adapter2);
		spinner3.setPrompt("Select Time");
		
		}
	
	//This method creates a link from Booking Page back to the Home Page.
	public void BackPage(){
	Button b =(Button) findViewById(R.id.button3);
	b.setOnClickListener(new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent e = new Intent(BookingPage.this, Home.class);
			startActivity(e);
		}
	});
	}
	public void SignOut(){
		Button b =(Button) findViewById(R.id.button2);
		b.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent e = new Intent(BookingPage.this, MainActivity.class);
				startActivity(e);
			}
		});
		}
	
	//Do something with this, supposed to get information from drop down list.
	public void addListenerOnButton(){
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1 = (Spinner) findViewById(R.id.spinner2);
		btn = (Button) findViewById(R.id.button1);
		
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Toast.makeText(BookingPage.this, "OnclickListener:"+
			"\nSpnner 1: "+ String.valueOf(spinner1.getSelectedItem())+ "Spinner 2 L " + String.valueOf(spinner2.getSelectedItem()),
			Toast.LENGTH_SHORT).show();
			}
		});	
	}
	
	
}


