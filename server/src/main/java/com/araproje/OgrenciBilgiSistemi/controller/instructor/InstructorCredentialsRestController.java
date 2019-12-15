package com.araproje.OgrenciBilgiSistemi.controller.instructor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Instructor;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;

@RestController
@RequestMapping("/api/rest/instructor/credentials")
public class InstructorCredentialsRestController {
	
	@Autowired
	InstructorService instructorService;
	
	@GetMapping
	public ResponseEntity<?> get(HttpServletRequest request){
		Instructor instructor;
		try {
			instructor = instructorService.get((String)request.getAttribute("uid"));
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(instructor);
	}
}
