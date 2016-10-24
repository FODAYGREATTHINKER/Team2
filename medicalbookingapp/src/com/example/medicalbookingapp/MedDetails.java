package com.example.medicalbookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Medical class for getting the layout of the medical center details file
 * 
 * 
 */
public class MedDetails extends Activity {
	private ImageButton medBack;
	/**
	 * When page is opened, gets layout of medical centre details
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medicaldetails);
		
		medBack = (ImageButton) findViewById(R.id.medBackBtn);
		medBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MedDetails.this, Home.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);

			}
		});
	}
}