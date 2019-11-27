package com.araproje.OgrenciBilgiSistemi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.CourseService;
import com.araproje.OgrenciBilgiSistemi.service.DepartmentService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;

@RestController
@RequestMapping("/api/rest/common")
public class commonController {

	@Autowired
	DepartmentService departmentService;
	@Autowired
	CourseService courseService;
	@Autowired
	ClassroomService classroomService;
	@Autowired
	SectionService sectionService;
	
	
	
	/*@GetMapping("/{id}/sections")
	public ResponseEntity<?> getSections(@PathVariable String id){
		Department department;
		List<Section> sectionsWithDept;
		try {
			 department = departmentService.get(Integer.parseInt(id));
			 sectionsWithDept = sectionService.getAll(department);
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(sectionsWithDept);
	}*/
}
