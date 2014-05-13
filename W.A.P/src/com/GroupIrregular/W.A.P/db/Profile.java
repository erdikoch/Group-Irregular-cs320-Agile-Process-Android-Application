package com.groupIrregular.wap.db;

import android.graphics.Bitmap;

public class Profile {
	
	private int id;
	private String userName;
	private String name;
	private String surname;
	private String phone;
	private String email;
	private Bitmap profilePhoto;
	
	public Profile() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {;
		return surname;
	}

	public void setSurname(String surname) {
		this.surname= surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Bitmap getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(Bitmap profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

}
