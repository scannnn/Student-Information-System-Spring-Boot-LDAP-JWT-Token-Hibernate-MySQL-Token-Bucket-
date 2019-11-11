package com.araproje.OgrenciBilgiSistemi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.repository.DepartmentRepository;
import com.araproje.OgrenciBilgiSistemi.security.JwtTokenProvider;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;

@RestController
@RequestMapping("/api/rest/student")
public class StudentRestController {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	ClassroomService classroomService;
	
	@PostMapping("/studentInfo")
	public ResponseEntity<?> getStudentInfo(HttpServletRequest request) {
		
		String header = request.getHeader("Authorization");
		String jwt = header.substring(7);
		return ResponseEntity.ok()
	    	      .body(jwtTokenProvider.getUserFromJWT(jwt));
	}
	
	@PostMapping("/deneme")
	public ResponseEntity<?> deneme(HttpServletRequest request){
		try {
			departmentRepository.save(new Department("EHM", "Elektronik ve Haberleşme Mühendisliği"));
			System.out.println("HEADERS = "+request.getHeader("classroomCode")+request.getHeader("departmentCode"));
			classroomService.create(request.getHeader("classroomCode"), request.getHeader("departmentCode"));
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok("OLMADI");
		}
		return ResponseEntity.ok()
	    	      .body("oldu");
	}
}
