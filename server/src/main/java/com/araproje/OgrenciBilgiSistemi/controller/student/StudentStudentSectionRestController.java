package com.araproje.OgrenciBilgiSistemi.controller.student;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.model.StudentSection;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;
import com.araproje.OgrenciBilgiSistemi.service.StudentSectionService;
import com.araproje.OgrenciBilgiSistemi.service.StudentService;

@RestController
@RequestMapping("/api/rest/student/studentsections")

public class StudentStudentSectionRestController {
	@Autowired
	StudentService studentService;
	@Autowired
	SectionService sectionService;
	@Autowired
	StudentSectionService studentSectionService;
	
	@GetMapping("/{year}/{term}")
	public ResponseEntity<?> getAll(@PathVariable String year, @PathVariable String term, HttpServletRequest request){
		Student student;
		List<StudentSection> studentSections;
		List<StudentSection> studentSectionsTemp;
		try {
			studentSections = new ArrayList<>();
			student = studentService.get((String)request.getAttribute("uid"));
			studentSectionsTemp = studentSectionService.getAll(student);
			for(StudentSection ss : studentSectionsTemp) {
				if(ss.getSection().getTerm().equalsIgnoreCase(term) && ss.getSection().getYear().equalsIgnoreCase(year)) {
					studentSections.add(ss);
				}
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(studentSections);
	}
}
