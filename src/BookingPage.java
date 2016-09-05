package com.example.medicalbookingapp;

import android.app.Activity;
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

public class BookingPage extends Activity {
	private Spinner spinner1, spinner2;
	private Button btn;
		
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_page);
		
		
	Spinner spinner = (Spinner) findViewById(R.id.spinner1);
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.medical_center, android.R.layout.simple_spinner_item);
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner.setAdapter(adapter);
	
	Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
	ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Doctors, android.R.layout.simple_spinner_item);
	adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner2.setAdapter(adapter1);
	
	Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
	ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Time, android.R.layout.simple_spinner_item);
	adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner3.setAdapter(adapter2);
	
	addListenerOnButton();
	}
	
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


