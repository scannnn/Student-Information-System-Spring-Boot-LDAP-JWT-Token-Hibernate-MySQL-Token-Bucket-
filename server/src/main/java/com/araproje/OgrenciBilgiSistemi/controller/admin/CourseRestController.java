package com.araproje.OgrenciBilgiSistemi.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.araproje.OgrenciBilgiSistemi.model.Course;
import com.araproje.OgrenciBilgiSistemi.service.CourseService;
import com.araproje.OgrenciBilgiSistemi.service.DepartmentService;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api/rest/admin/courses")
public class CourseRestController {

	@Autowired
	CourseService courseService;
	@Autowired
	DepartmentService departmentService;

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Map<String, Object> JSON){
		try {
			List<Course> prerequisites = new ArrayList<>();
			List<String> prerequisitiesCodes = (List<String>) JSON.get("prerequisites");
			for(String courseCode : prerequisitiesCodes) {
				prerequisites.add(courseService.get(courseCode));
			}
			courseService.create((String)JSON.get("courseCode"), (String)JSON.get("title"),
					departmentService.get((String)JSON.get("departmentCode")), (int)JSON.get("credit"),
					 (String)JSON.get("language"), prerequisites);
		}
		catch(Exception e){
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(courseService.get((String)JSON.get("courseCode")));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id){
		try {
			courseService.delete(Integer.parseInt(id));
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
		List<Course> courses;
		try {
			courses = courseService.getAll();
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(courses);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable String id){
		Course course;
		try {
			course = courseService.get(Integer.parseInt(id));
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(course);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAllFields(@PathVariable String id, @RequestBody Map<String, Object> JSON){
		try {
			if(courseService.isExist(Integer.parseInt(id))) {
				List<Course> prerequisities = new ArrayList<>();
				List<String> prerequisitiesCodes = (List<String>) JSON.get("prerequisites");
				for(String courseCode : prerequisitiesCodes) {
					prerequisities.add(courseService.get(courseCode));
				}
				courseService.update(new Course((String)JSON.get("courseCode"), (String)JSON.get("title"),
					departmentService.get((String)JSON.get("departmentCode")), (int)JSON.get("credit"),
					(String)JSON.get("language"), prerequisities), Integer.parseInt(id));
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED).body((String)JSON.get("courseCode"));
	}
}
