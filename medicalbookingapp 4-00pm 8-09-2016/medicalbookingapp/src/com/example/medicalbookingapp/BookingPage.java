package com.example.medicalbookingapp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
//Page for making bookings.
public class BookingPage extends Activity implements OnItemSelectedListener {
    private Spinner spinner, spinner2, spinner3;
    private Button btn;
    Button b;
    String text;
    BookingDatabase bd;
    EditText date;
    String name = Home.getusername();
    ArrayAdapter<CharSequence> adapter1;
    TextView error;
    /*
     * When the page is opened, this method creates the functions / methods for
     * the booking page.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);
        bd = new BookingDatabase(getBaseContext());
        createSpinners();
        addListenerOnButton();
        BackPage();
        SignOut();
        confirm();
    }
    /* Creates the three spinners and inserts data from array. */
    public void createSpinners() {
        spinner = (Spinner) findViewById(R.id.spinner1);
        // outputs message when something is selected.
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.medical_center,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select Medical Center");
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(null);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        // outputs message when something is selected.
        spinner3.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.Time, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Throw in that next level for loop.
        spinner3.setAdapter(adapter2);
        spinner3.setPrompt("Select Time");
    }
    public void setupSpinner2() {
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        if (spinner.getSelectedItem().equals("Mubas Medical")) {
            adapter1 = ArrayAdapter.createFromResource(this, R.array.Doctors1,
                    android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter1);
            spinner2.setPrompt("Select Doctor");
        } else if (spinner.getSelectedItem().equals("Shortland Street")) {
            adapter1 = ArrayAdapter.createFromResource(this, R.array.Doctors2,
                    android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter1);
            spinner2.setPrompt("Select Doctor");
        }
    }
    /* gets the information from application and stores it in booking database. */
    //checks for errors.
    public void confirm() {
        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner = (Spinner) findViewById(R.id.spinner1);
                spinner2 = (Spinner) findViewById(R.id.spinner2);
                spinner3 = (Spinner) findViewById(R.id.spinner3);
                date = (EditText) findViewById(R.id.Text1);
                String med = spinner.getSelectedItem().toString();
                String doctor = spinner2.getSelectedItem().toString();
                String time = spinner3.getSelectedItem().toString();
                String dates = String.valueOf(date.getText().toString());
                
                if(dateEmpty()==true && dateLength()==true && dateFormat()==true && 
                checkTime("Patient_Bookings","MedicalCentres",med,"DoctorName",doctor,"Booking_Date",dates,"Booking_Time",time)== true
                		  && isDateWithin3Months(date.getText().toString(), "dd/MM/yyyy")==true){
                bd.insertBooking(name, med, doctor, dates, time);
                bd.close();
                finish();
                Toast.makeText(getApplicationContext(), "Booking confirmed", Toast.LENGTH_LONG).show();
                }
                }
                
            
        });
    }
    //Checks if the date is empty.
    public boolean dateEmpty(){
    	error= (TextView) findViewById(R.id.errorDate);
    	if(date.getText().toString().length()==0){
    		
    		error.setText("please input a date");
    		return false;
    	}
    	return true;
    }
    public boolean dateLength(){
    	if(date.getText().toString().length()>10){
    		error.setText("Date length too long");
    		return false;
    	}
    	return true;
    }
    //Check if the date format is true.
    public boolean dateFormat(){
    	int year;
    	SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
    	try{
    		Date dates = fm.parse(date.getText().toString());
    		if(new Date().after(dates)){
    		error.setText("Can't book past dates");
    		return false;
    		
    		}
    		return true;
    	}
    	catch(ParseException e){
    		error.setText("Wrong format for date");
    		return false;
    	}
    }
    
    //check date
    public boolean isDateWithin3Months(String dateToValidate, String dateFormat){
    	SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    	sdf.setLenient(false);
    	try{
    			Date date = sdf.parse(dateToValidate);
    			Calendar currentDateAfter3Months = Calendar.getInstance();
    			currentDateAfter3Months.add(Calendar.MONTH, 3);	
    			
    			// current date before 3 months
    			Calendar currentDateBefore3Months = Calendar.getInstance();
    			currentDateBefore3Months.add(Calendar.MONTH, -3);
    			
    			if (date.before(currentDateAfter3Months.getTime())
    					&& date.after(currentDateBefore3Months.getTime())) {

    				//ok everything is fine, date in range
    				return true;

    			} else {
    				error.setText("Book only within 3 months");
    				return false;
    				
    			}

    		} catch (ParseException e) {

    			e.printStackTrace();
    			
    			return false;
    		}
    	

    	
    }
    
    //checks if date and time is already taken.
    public boolean checkTime(String tableName,String med, String medField, String Doc, String docField, String Booking, String BookField, String Time, String timeField){
    	
    	BookingDatabase helper = new BookingDatabase(this);
    	SQLiteDatabase bd = helper.getReadableDatabase();
    	
    	String Query = "Select * from " + tableName + " where " + med+ " LIKE '%" + medField + "%' AND " + Doc+ " LIKE '%" + docField + "%' AND " + Booking + " LIKE '%" + BookField + "%' AND " 
    					+ Time + " LIKE '%" + timeField + "%'"; ;  
    	Cursor c = bd.rawQuery(Query, null);
    	if((c.getCount() <=0)){
    		
    		c.close();
    		return true;
    	}
    	
    	error.setText("Time already booked.");
    	c.close();
    	return false;
    	
    }
    
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    // This method creates a link from Booking Page back to the Home Page.
    public void BackPage() {
        Button b = (Button) findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /* Signs out of the application if users don't want to make a booking. */
    public void SignOut() {
        Button b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(BookingPage.this, MainActivity.class);
                startActivity(e);
            }
        });
    }
    // Do something with this, supposed to get information from drop down list.
    public void addListenerOnButton() {
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        BookingPage.this,
                        "OnclickListener:" + "\nSpnner 1: "
                                + String.valueOf(spinner.getSelectedItem())
                                + "Spinner 2 L "
                                + String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}