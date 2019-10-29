package com.araproje.OgrenciBilgiSistemi.model;

public class User {
	private String userName;
	private String userId;
	private String role;
	
	public User() {}
	
	public User(String userName, String userId, String role) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.role = role;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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

}
