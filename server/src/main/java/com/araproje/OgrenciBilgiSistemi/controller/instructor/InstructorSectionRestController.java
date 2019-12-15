package com.araproje.OgrenciBilgiSistemi.controller.instructor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/api/rest/instructor/sections")
public class InstructorSectionRestController {
	@Autowired
	InstructorService instructorService;
	@Autowired
	SectionService sectionService;
	
	@GetMapping("/{year}/{term}")
	public ResponseEntity<?> getSections(@PathVariable String year, @PathVariable String term, HttpServletRequest request){
		Instructor instructor;
		List<Section> sections;
		try {
			instructor = instructorService.get((String)request.getAttribute("uid"));
			sections = sectionService.getByYearAndTermAndInstructor(year, term, instructor);
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(sections);
	}
}
