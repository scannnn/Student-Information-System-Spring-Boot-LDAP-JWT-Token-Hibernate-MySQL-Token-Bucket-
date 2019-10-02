package com.araproje.OgrenciBilgiSistemi.model;

import java.util.ArrayList;

public class User {
	private String userName;
	private String userId;
	private ArrayList<String> roles = new ArrayList<>();
	
	public User() {}
	
	public User(String userName, String userId, ArrayList<String> roles) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.roles = roles;
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
	public ArrayList<String> getRoles() {
		return roles;
	}
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}
}
