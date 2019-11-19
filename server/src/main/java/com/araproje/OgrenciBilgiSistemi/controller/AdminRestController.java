package com.araproje.OgrenciBilgiSistemi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.repository.DepartmentRepository;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;

@RestController
@RequestMapping("/api/rest/admin")
public class AdminRestController {

	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	ClassroomService classroomService;
	
	@PutMapping("/department/add")
	public ResponseEntity<?> deneme(@RequestBody Department department){
		System.out.println("BURADA");
		try {
			departmentRepository.save(new Department(department.getDepartmentCode(), department.getTitle()));
			System.out.println(department);
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("amucuk");
		}
		return ResponseEntity.ok()
	    	      .body(departmentRepository.findByDepartmentCode(department.getDepartmentCode()));
	}
}
