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
import com.araproje.OgrenciBilgiSistemi.model.GradeType;
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
			@RequestBody Map<String, Integer> JSON, HttpServletRequest request){
		Grade vize1, vize2, proje, fin;
		GradeType v1, v2, p, f;
		StudentSection studentSection;
		try {
			v1 = gradeTypeService.get("Vize 1");
			v2 = gradeTypeService.get("Vize 2");
			f = gradeTypeService.get("Final");
			p = gradeTypeService.get("Proje");
			studentSection = studentSectionService.get(Integer.parseInt(studentSectionId));
			
			if(JSON.containsKey("Vize 1")) {
				if(gradeService.isExist(studentSection, v1)) {
					System.out.println("Hello 1");
					vize1 = gradeService.get(studentSection, v1);
					vize1.setGrade(JSON.get("Vize 1"));
					gradeService.update(vize1);
				}
				else {
					System.out.println("Hello 2");
					gradeService.create(studentSection, v1 , JSON.get("Vize 1"));
				}
			}
			
			if(JSON.containsKey("Vize 2")) {
				if(gradeService.isExist(studentSection, v2)) {
					vize2 = gradeService.get(studentSection, v2);
					vize2.setGrade(JSON.get("Vize 2"));
					gradeService.update(vize2);
				}
				else {
					gradeService.create(studentSection, v2 , JSON.get("Vize 2"));
				}
			}
			
			if(JSON.containsKey("Final")) {
				if(gradeService.isExist(studentSection, f)) {
					fin = gradeService.get(studentSection, f);
					fin.setGrade(JSON.get("Final"));
					gradeService.update(fin);
				}
				else {
					gradeService.create(studentSection, f , JSON.get("Final"));
				}
			}
			
			if(JSON.containsKey("Proje")) {
				if(gradeService.isExist(studentSection, p)) {
					proje = gradeService.get(studentSection, p);
					proje.setGrade(JSON.get("Proje"));
					gradeService.update(proje);
				}
				else {
					gradeService.create(studentSection, p , JSON.get("Proje"));
				}
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
