package com.araproje.OgrenciBilgiSistemi.security;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

import com.araproje.OgrenciBilgiSistemi.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${app.jwtSecret}")
    private String jwtSecret;
	
	@Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication) {

    	LdapUserDetailsImpl userPrincipal = (LdapUserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        Claims claims = Jwts.claims()
				.setExpiration(expiryDate)
				.setSubject(userPrincipal.getUsername());
        claims.put("roles", userPrincipal.getAuthorities());
        System.out.println(userPrincipal.getAuthorities().getClass());
		// USERID EKLE
        
        return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, "usis")
				.compact();
    }
	
	@SuppressWarnings("unchecked")
	public User getUserFromJWT(String token) {
		
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        
        User user = new User();
        user.setUserName(claims.getSubject());
        user.setRoles((ArrayList<String>) claims.get("roles"));
        System.out.println("DENEME= "+user.getRoles());
        //USERID EKLE KOY
        return user;
    }
	
	public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
