package com.araproje.OgrenciBilgiSistemi.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Classroom;
import com.araproje.OgrenciBilgiSistemi.model.Course;
import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.CourseService;
import com.araproje.OgrenciBilgiSistemi.service.DepartmentService;

@RestController
@RequestMapping("/api/rest/admin/departments")
public class DepartmentRestController {
	
	@Autowired
	DepartmentService departmentService;
	@Autowired
	CourseService courseService;
	@Autowired
	ClassroomService classroomService;
	
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
	
	@GetMapping("/{id}/courses")
	public ResponseEntity<?> getCourses(@PathVariable String id){
		Department department;
		List<Course> coursesWithDept;
		try {
			 department = departmentService.get(Integer.parseInt(id));
			 coursesWithDept = courseService.getAll(department);
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(coursesWithDept);
	}
	
	@GetMapping("/{id}/classrooms")
	public ResponseEntity<?> getClassrooms(@PathVariable String id){
		Department department;
		List<Classroom> classroomsWithDept;
		try {
			 department = departmentService.get(Integer.parseInt(id));
			 classroomsWithDept = classroomService.getAll(department);
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(classroomsWithDept);
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
