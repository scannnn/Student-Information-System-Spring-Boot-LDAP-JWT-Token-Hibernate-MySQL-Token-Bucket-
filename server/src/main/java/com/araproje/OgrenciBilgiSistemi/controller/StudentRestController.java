package com.araproje.OgrenciBilgiSistemi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/rest/student")
public class StudentRestController {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/getStudentInfo")
	public Object getStudentInfo(HttpServletRequest request) {
		
		String header = request.getHeader("Authorization");
		if(header == null || !header.startsWith("Bearer")) {
			throw new RuntimeException("JWT Token is missing!");
		}
		
   	 	String jwt = header.substring(7);
   	 	if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
   	 		return jwtTokenProvider.getUserFromJWT(jwt);
   	 	}
   	 	else {
   	 		return "ERROR MESSAGE";
   	 	}
	}
}
