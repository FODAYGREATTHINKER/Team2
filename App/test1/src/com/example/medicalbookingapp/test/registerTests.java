package com.example.medicalbookingapp.test;

import org.junit.Test;

import com.example.medicalbookingapp.PatientRegister;
import com.example.medicalbookingapp.R.id;

import android.R;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;

public class registerTests extends
		ActivityInstrumentationTestCase2<PatientRegister> {
	EditText name_in, username_in, pass_in,pass_in_again, age_in;

	@SuppressWarnings("deprecation")
	public registerTests() {
		super("com.example.medicalbookingapp", PatientRegister.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		PatientRegister pr = getActivity();

		// gets and assign the elements from activity
		username_in = (EditText) getActivity().findViewById(id.usernamein);
		pass_in = (EditText) getActivity().findViewById(id.passwordin);
		pass_in_again = (EditText) getActivity().findViewById(id.passwordagain);
		name_in = (EditText) getActivity().findViewById(id.namein);
		age_in = (EditText) getActivity().findViewById(id.editText1);

	}

	/**
	 * Testvalidage method will check if the user user enter valid integer 
	 * as their age.
	 */
	@UiThreadTest
	public void testvalidage() {
		
		age_in.append("19");
		//checks with the method in the class
		Boolean incorrectage=this.getActivity().checkvalidage(Integer.valueOf(age_in.getText().toString()));
		assertFalse(incorrectage.booleanValue()==true);
	}

	/**
	 * testifusernameisinuse method will check user entered username 
	 * is already in the system.
	 */
	@UiThreadTest
	public void testifusernameisinuse() {
		username_in.append("u123");
		//checks with the method in the class
		Boolean usernameexist=this.getActivity().checkprevioususernames(username_in.getText().toString());
		assertFalse(usernameexist.booleanValue()==true);
	
	}
	/**
	 * testforsamepassword will test if both password 
	 * user entered was the same.
	 */
	@UiThreadTest
	public void testforsamepassword() {
		pass_in.append("19");
		pass_in_again.append("19");
		//checks with the method in the class
		Boolean wrongpassword=this.getActivity().checkforequalpasswords(pass_in.getText().toString(), pass_in_again.getText().toString());
		assertFalse(wrongpassword.booleanValue()==true);
	
	}
	
	/**
	 * testempty method will test if the username is empty.
	 */
	@UiThreadTest
	public void testemptyname() {
		name_in.append("yo");
		assertTrue(name_in.getText().length()>=1);

	}

}

