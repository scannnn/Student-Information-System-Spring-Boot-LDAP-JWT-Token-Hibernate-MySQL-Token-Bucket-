package com.araproje.OgrenciBilgiSistemi.model;

public class User {
	private String userId;
	private String fullName;
	private String lastName;
	private String role;
	
	public User() {}
	
	public User(String userId, String fullName, String lastName, String role) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.lastName = lastName;
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
