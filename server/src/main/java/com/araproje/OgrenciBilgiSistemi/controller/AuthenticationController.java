package com.araproje.OgrenciBilgiSistemi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.payload.ApiResponse;
import com.araproje.OgrenciBilgiSistemi.payload.JwtAuthenticationResponse;
import com.araproje.OgrenciBilgiSistemi.payload.LoginRequest;
import com.araproje.OgrenciBilgiSistemi.security.JwtTokenProvider;
import com.araproje.OgrenciBilgiSistemi.util.MessageConstants;

@RestController
@RequestMapping("/api/auth")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/generatetoken")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
		
		if(loginRequest.getUsername().isEmpty() || loginRequest.getPassword().isEmpty()) {
			return new ResponseEntity(new ApiResponse(false, MessageConstants.USERNAME_OR_PASSWORD_INVALID), HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
		
		String jwt = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
	
	@PostMapping("/validatetoken")
	public ResponseEntity<?> getTokenByCredentials(HttpServletRequest request){
		
		String header = request.getHeader("Authorization");
		
		if(header == null || !header.startsWith("Bearer")) {
			throw new RuntimeException("JWT Token is missing!");
		}
		
		String username = null;
   	 	String jwt = header.substring(7);
   	 	if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
   	 		username = jwtTokenProvider.getUserFromJWT(jwt).getUserName();
   	 		return new ResponseEntity(new ApiResponse(true,MessageConstants.VALID_TOKEN + username), HttpStatus.OK);
   	 	}
   	 	else {
   	 		return new ResponseEntity(new ApiResponse(false, MessageConstants.INVALID_TOKEN),
                HttpStatus.BAD_REQUEST);
   	 	}
	}
}
