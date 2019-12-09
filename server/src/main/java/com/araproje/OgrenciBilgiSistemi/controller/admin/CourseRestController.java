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
		Course c;
		String course_code;
		try {
			course_code = (String)JSON.get("courseCode");
			if(!courseService.isExist(course_code)) {
				System.out.println("girdi");
				List<Course> prerequisites = new ArrayList<>();
				List<String> prerequisitiesCodes = (List<String>) JSON.get("prerequisites");
				for(String courseCode : prerequisitiesCodes) {
					prerequisites.add(courseService.get(courseCode));
				}
				courseService.create(course_code, (String)JSON.get("title"),
						departmentService.get((String)JSON.get("departmentCode")),
						(int)JSON.get("credit"), (String)JSON.get("language"), prerequisites);
				c = courseService.get(course_code);
				System.out.println(c.getCourseCode());
			}
			else throw new Exception("Eklemeye çalıştığınız ders kodunda hali hazırda bir ders mevcut.");
		}
		catch(Exception e){
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(c);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id){
		Course c;
		Object trick;
		try {
			c = courseService.get(Integer.parseInt(id));
			trick = c.getPrerequisites();		// lazy loader hatasından kurtulmak için tricky way
			trick = c.getWhosprerequisites();
			courseService.delete(Integer.parseInt(id));
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED).body(c);
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
		Course c = null;
		try {
			if(courseService.isExist(Integer.parseInt(id))) {
				List<Course> prerequisities = new ArrayList<>();
				List<String> prerequisitiesCodes = (List<String>) JSON.get("prerequisites");
				for(String courseCode : prerequisitiesCodes) {
					prerequisities.add(courseService.get(courseCode));
				}
				c = new Course((String)JSON.get("courseCode"), (String)JSON.get("title"),
						departmentService.get((String)JSON.get("departmentCode")),
						(int)JSON.get("credit"), (String)JSON.get("language"), prerequisities);
				courseService.update(c, Integer.parseInt(id));
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED).body(c);
	}
}
