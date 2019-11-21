package com.araproje.OgrenciBilgiSistemi.controller.admin;

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
import com.araproje.OgrenciBilgiSistemi.service.DepartmentService;

@RestController
@RequestMapping("/api/rest/admin/departments")
public class DepartmentRestController {
	
	@Autowired
	DepartmentService departmentService;
	
	@PostMapping
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
	
	
	@DeleteMapping("/{id}")
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
	
	@GetMapping
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
	
	@GetMapping("/{id}")
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
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateSomeFields(@PathVariable String id, @RequestBody Department department){
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
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAllFields(@PathVariable String id, @RequestBody Department department){
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
