package com.araproje.OgrenciBilgiSistemi.payload;

import java.util.Date;

public class IpBlock {
	int warningCount;
	int banCount;
	boolean isBanned;
	Date lastRequest;
	Date bannedUntil;
	
	public IpBlock() {
		super();
		warningCount = 0;
		banCount = 0;
		isBanned = false;
		bannedUntil = new Date(Long.MIN_VALUE);
		lastRequest = new Date(Long.MIN_VALUE);
	}
	
	public int getWarningCount() {
		return warningCount;
	}
	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}
	public int getBanCount() {
		return banCount;
	}
	public void setBanCount(int banCount) {
		this.banCount = banCount;
	}
	public boolean isBanned() {
		Date currentDate = new Date(System.currentTimeMillis());
		if(currentDate.after(bannedUntil)) {
			isBanned = false;
		}
		else {
			isBanned = true;
		}
		return isBanned;
	}
	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}
	public Date getLastRequest() {
		return lastRequest;
	}
	public void setLastRequest(Date lastRequest) {
		this.lastRequest = lastRequest;
	}
	public Date getBannedUntil() {
		return bannedUntil;
	}
	public void setBannedUntil(Date bannedUntil) {
		this.bannedUntil = bannedUntil;
	}
	public void increaseWarningCount() {
		warningCount++;
		if(warningCount == 6) {
			warningCount = 0;
			banCount++;
			if(banCount == 3) {
				bannedUntil =  new Date(1800000);
			}
			else {
				bannedUntil =  new Date(System.currentTimeMillis() + 20000);
			}
		}
	}
}
