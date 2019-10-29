package com.araproje.OgrenciBilgiSistemi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.araproje.OgrenciBilgiSistemi.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/rest/student")
public class StudentRestController {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/studentInfo")
	public ResponseEntity<?> getStudentInfo(HttpServletRequest request) {
		
		String header = request.getHeader("Authorization");
		String jwt = header.substring(7);
		return ResponseEntity.ok()
	    	      .body(jwtTokenProvider.getUserFromJWT(jwt));
	}
	
}
