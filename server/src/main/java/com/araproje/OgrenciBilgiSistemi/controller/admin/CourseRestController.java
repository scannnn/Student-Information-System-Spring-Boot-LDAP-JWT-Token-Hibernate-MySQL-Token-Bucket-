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

@RestController
@RequestMapping("/api/rest/admin/courses")
public class CourseRestController {

	@Autowired
	CourseService courseService;
	@Autowired
	DepartmentService departmentService;
	
	// derse bağlı dersleri eklemeyi konuşun nasıl olsun ne atılsın. Department nasıl atılsın bunu konuşun
	// department i departmentCode olarak mı atıcan sadece yoksa direkt koyulucak department i JSON olarak bana mı atıcaksın
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Map<String, String> JSON){
		try {
			courseService.create(JSON.get("courseCode"), JSON.get("title"),
					departmentService.get(JSON.get("departmentCode")));
		}
		catch(Exception e){
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(courseService.get(JSON.get("courseCode")));
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
	
	// BAĞLI OLDUGU DERSLERİ DERS OBJESİ OLARAK MI YOKSA DERS KODU OLARAK MI ATILACAK?
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAllFields(@PathVariable String id, @RequestBody Map<String, String> JSON){
		try {
			if(courseService.isExist(Integer.parseInt(id))) {
				List<Course> prerequisities = new ArrayList<>();
				String[] prerequisitiesCodes = null;		// JSON OBJESİNDEN ALDIĞIN ÖN KOŞULLAR LİSTESİNİ ÇEVİRMEYE ÇALIŞ KONUŞ
				for(String courseCode : prerequisitiesCodes) {
					prerequisities.add(courseService.get(courseCode));
				}
				courseService.update(new Course(JSON.get("courseCode"), JSON.get("title"),
					departmentService.get(JSON.get("departmentCode")), prerequisities), Integer.parseInt(id));
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED).body(courseService.get(Integer.parseInt(id)));
	}
}
