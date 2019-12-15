package com.araproje.OgrenciBilgiSistemi.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.araproje.OgrenciBilgiSistemi.model.Instructor;
import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.payload.ApiResponse;
import com.araproje.OgrenciBilgiSistemi.payload.LoginRequest;
import com.araproje.OgrenciBilgiSistemi.security.JwtTokenProvider;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.StudentService;
import com.araproje.OgrenciBilgiSistemi.util.MessageConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/auth")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	StudentService studentService;
	@Autowired
	InstructorService instructorService;
	
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
		
		Claims claim = jwtTokenProvider.generateToken(authentication);
		String token = Jwts.builder().setClaims(claim).signWith(SignatureAlgorithm.HS512, "usis").compact();
		Map<String, Object> jo = new HashMap<>();
		jo.put("accessToken", token);
		jo.put("expiresInMillis", claim.getExpiration().getTime());
		if(jwtTokenProvider.getUserFromJWT(token).getRole().equalsIgnoreCase("Student")) {
			Student student = studentService.get(loginRequest.getUsername());
			System.out.println(student.toString());
			jo.put("user", student);
			jo.put("role", jwtTokenProvider.getUserFromJWT(token).getRole());
		}
		else if(jwtTokenProvider.getUserFromJWT(token).getRole().equalsIgnoreCase("Instructor")) {
			Instructor instructor = instructorService.get(loginRequest.getUsername());
			jo.put("user", instructor);
			jo.put("role",jwtTokenProvider.getUserFromJWT(token).getRole());
		}
		else {
			jo.put("user", "ADMINISTRATOR");
			jo.put("role", jwtTokenProvider.getUserFromJWT(token).getRole());
		}
		
		return ResponseEntity.ok(jo);
	}
	
	@PostMapping("/validatetoken")
	public ResponseEntity<?> getTokenByCredentials(HttpServletRequest request){
		
		String header = request.getHeader("Authorization");
		
		if(header == null || !header.startsWith("Bearer")) {
			return new ResponseEntity(new ApiResponse(false, MessageConstants.EMPTY_TOKEN),
	                HttpStatus.BAD_REQUEST);
		}
		else {
			String username = null;
	   	 	String jwt = header.substring(7);
		   	 if(StringUtils.hasText(jwt)) {
		   		try {
			   		 jwtTokenProvider.validateToken(jwt);
			   	 }catch(Exception ex) {
			   		return new ResponseEntity(new ApiResponse(false, ex.getMessage()),
			                HttpStatus.BAD_REQUEST);
			   	 }
		   	 }
	   	 	username = jwtTokenProvider.getUserFromJWT(jwt).getUserId();
	 		return new ResponseEntity(new ApiResponse(true,MessageConstants.VALID_TOKEN + username), HttpStatus.OK);
		}
	}
}
