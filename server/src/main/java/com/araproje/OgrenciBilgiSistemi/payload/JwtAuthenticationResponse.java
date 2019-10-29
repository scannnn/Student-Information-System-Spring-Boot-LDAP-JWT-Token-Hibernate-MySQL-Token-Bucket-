package com.araproje.OgrenciBilgiSistemi.payload;

public class JwtAuthenticationResponse {
    private String accessToken;
    private long expiresInMillis;

    public JwtAuthenticationResponse(String accessToken, long expiresInMillis) {
        this.accessToken = accessToken;
        this.expiresInMillis = expiresInMillis;
    }
    
    public JwtAuthenticationResponse() {
    	
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

	public long getExpiresInMillis() {
		return expiresInMillis;
	}

	public void setExpiresInMillis(long expiresInMillis) {
		this.expiresInMillis = expiresInMillis;
	}
    
}