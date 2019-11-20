package com.araproje.OgrenciBilgiSistemi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.DepartmentService;

@RestController
@RequestMapping("/api/rest/admin")
public class AdminRestController {

	@Autowired
	DepartmentService departmentService;
	@Autowired
	ClassroomService classroomService;
	
	@PostMapping("/departments")
	public ResponseEntity<?> add(@RequestBody Department department){
		try {
			departmentService.create(department.getDepartmentCode(), department.getTitle());
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				  .status(HttpStatus.CREATED)
	    	      .body(departmentService.get(department.getDepartmentCode()));
	}
	
	
	@DeleteMapping("/departments/{id}")
	public ResponseEntity<?> delete(@PathVariable String id){
		try {
			departmentService.delete(Integer.parseInt(id));
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED).body("Deleted.");
	}
	
	@GetMapping("/departments")
	public ResponseEntity<?> get(){
		List<Department> departments;
		try {
			 departments = departmentService.getAll();
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(departments);
	}
	
	@GetMapping("/departments/{id}")
	public ResponseEntity<?> getOne(@PathVariable String id){
		Department department;
		try {
			 department = departmentService.get(Integer.parseInt(id));
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(department);
	}
	
	// PATCH İ TAMAMLA
	@PatchMapping("/departments/{id}")
	public ResponseEntity<?> update(@PathVariable String id, @RequestBody Department department){
		try {
			if(departmentService.isExist(Integer.parseInt(id))) {
				departmentService.updateParticial(department, Integer.parseInt(id));
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED).body(department);
	}
	
	@PutMapping("/departments/{id}")
	public ResponseEntity<?> updateAll(@PathVariable String id, @RequestBody Department department){
		try {
			if(departmentService.isExist(Integer.parseInt(id))) {
				departmentService.update(department, Integer.parseInt(id));
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED).body(department);
	}
}
