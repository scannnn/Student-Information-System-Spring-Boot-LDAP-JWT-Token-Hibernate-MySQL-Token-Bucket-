package com.araproje.OgrenciBilgiSistemi.controller.instructor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Grade;
import com.araproje.OgrenciBilgiSistemi.model.StudentSection;
import com.araproje.OgrenciBilgiSistemi.service.GradeService;
import com.araproje.OgrenciBilgiSistemi.service.GradeTypeService;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;
import com.araproje.OgrenciBilgiSistemi.service.StudentSectionService;

@RestController
@RequestMapping("/api/rest/instructor/grades")
public class InstructorGradeRestController {

	@Autowired
	InstructorService instructorService;
	@Autowired
	SectionService sectionService;
	@Autowired
	GradeService gradeService;
	@Autowired
	StudentSectionService studentSectionService;
	@Autowired
	GradeTypeService gradeTypeService;
	
	@PostMapping("/{studentSectionId}")
	public ResponseEntity<?> updateGrades(@PathVariable String studentSectionId, 
			@RequestBody Map<String, Object> JSON, HttpServletRequest request){
		Grade vize1, vize2, proje, fin;
		StudentSection studentSection;
		try {
			studentSection = studentSectionService.get(Integer.parseInt(studentSectionId));
			if(JSON.containsKey("Vize 1")) {
				vize1 = gradeService.get(studentSection, gradeTypeService.get("Vize 1"));
				vize1.setGrade(Long.parseLong((String)JSON.get("Vize 1")));
				gradeService.update(vize1);
			}
			
			if(JSON.containsKey("Vize 2")) {
				vize2 = gradeService.get(studentSection, gradeTypeService.get("Vize 2"));
				vize2.setGrade(Long.parseLong((String)JSON.get("Vize 2")));
				gradeService.update(vize2);
			}
			
			if(JSON.containsKey("Final")) {
				fin = gradeService.get(studentSection, gradeTypeService.get("Final"));
				fin.setGrade(Long.parseLong((String)JSON.get("Final")));
				gradeService.update(fin);
			}
			
			if(JSON.containsKey("Proje")) {
				proje = gradeService.get(studentSection, gradeTypeService.get("Proje"));
				proje.setGrade(Long.parseLong((String)JSON.get("Proje")));
				gradeService.update(proje);
			}
			
			studentSection = studentSectionService.get(Integer.parseInt(studentSectionId));
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(studentSection);
	}
}
