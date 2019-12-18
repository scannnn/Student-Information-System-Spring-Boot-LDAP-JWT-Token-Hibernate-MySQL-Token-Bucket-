package com.araproje.OgrenciBilgiSistemi.controller.student;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.service.StudentService;

@RestController
@RequestMapping("/api/rest/student/credentials")
public class StudentCredentialsRestController {
	@Autowired
	StudentService studentService;
	
	@GetMapping
	public ResponseEntity<?> get(HttpServletRequest request){
		Student student;
		try {
			student = studentService.get((String)request.getAttribute("uid"));
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(student);
	}
	
}
