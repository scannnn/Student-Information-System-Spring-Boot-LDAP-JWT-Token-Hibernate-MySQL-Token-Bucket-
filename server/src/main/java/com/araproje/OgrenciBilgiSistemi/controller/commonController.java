package com.araproje.OgrenciBilgiSistemi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Instructor;
import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;

@RestController
@RequestMapping("/api/rest/common")
public class CommonController {
	
	@Autowired
	SectionService sectionService;
	@Autowired
	InstructorService instructorService;
	
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
}
