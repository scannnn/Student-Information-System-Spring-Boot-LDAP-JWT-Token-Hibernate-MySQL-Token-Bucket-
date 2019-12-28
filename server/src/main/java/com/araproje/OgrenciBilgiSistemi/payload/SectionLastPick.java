package com.araproje.OgrenciBilgiSistemi.payload;

import java.util.Date;

public class SectionLastPick {
	String studentCode;
	String courseCode;
	Date lastPick;
	
	public SectionLastPick(String studentCode, String courseCode) {
		super();
		this.studentCode = studentCode;
		this.courseCode = courseCode;
		this.lastPick = new Date(System.currentTimeMillis());
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public Date getLastPick() {
		return lastPick;
	}
	public void setLastPick(Date lastPick) {
		this.lastPick = lastPick;
	}
	
	public boolean chechForRePick() {
		Date now = new Date(System.currentTimeMillis());
		if(now.getTime() - lastPick.getTime() > 300000) {
			return true;
		}
		else return false;
	}
}
