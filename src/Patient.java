package com.example.medicalbookingapp;

public class Patient {

	private String name;
	private String Username;
	private String password;

	/*
	 * private static Patient instance =new Patient(); private Patient(){};
	 * 
	 * private static Patient getInstance(){ return instance; }
	 */

	public Patient(String name, String Username, String Password) {
		this.name = name;
		this.Username = Username;
		this.password = Password;

	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}
}
