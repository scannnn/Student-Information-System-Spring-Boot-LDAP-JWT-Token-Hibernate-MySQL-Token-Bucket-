package com.araproje.OgrenciBilgiSistemi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Instructor;
import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;
import com.araproje.OgrenciBilgiSistemi.service.StudentService;

@RestController
@RequestMapping("/api/rest/common")
public class CommonController {
	
	@Autowired
	SectionService sectionService;
	@Autowired
	InstructorService instructorService;
	@Autowired
	StudentService studentService;
	
	@GetMapping("/sections/{year}/{term}")
	public ResponseEntity<?> getByYearAndTerm(@PathVariable String year, @PathVariable String term){
		List<Section> sections;
		try {
			sections = sectionService.getByYearAndTerm(year, term);
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(sections);
	}
	
	@GetMapping("/instructors")
	public ResponseEntity<?> getInstructors(){
		List<Instructor> instructors;
		try {
			instructors = instructorService.getAll();
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(instructors);
	}
	
	@PutMapping("/credentials")
	public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Map<String, String> JSON){
		String role;
		Student student = null;
		Instructor instructor = null;
		try {
			role = (String)request.getAttribute("role");
			if(role.equalsIgnoreCase("Student")) {
				student = studentService.get((String)request.getAttribute("uid"));
				student.setAddress(JSON.get("address"));
				student.setDistrict(JSON.get("district"));
				student.setProvince(JSON.get("province"));
				student.setPhoneNumber(JSON.get("phoneNumber"));
				student.setMail(JSON.get("mail"));
				studentService.update(student);
			}
			else{
				instructor = instructorService.get((String)request.getAttribute("uid"));
				instructor.setAddress(JSON.get("address"));
				instructor.setDistrict(JSON.get("district"));
				instructor.setProvince(JSON.get("province"));
				instructor.setPhoneNumber(JSON.get("phoneNumber"));
				instructor.setMail(JSON.get("mail"));
				instructorService.update(instructor);
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		if(role.equalsIgnoreCase("Student")) {
			return ResponseEntity
					.status(HttpStatus.OK).body(student);
		}
		else{
			return ResponseEntity
					.status(HttpStatus.OK).body(instructor);
		}
	}
}
