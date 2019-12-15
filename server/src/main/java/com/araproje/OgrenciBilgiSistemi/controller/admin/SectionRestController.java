package com.araproje.OgrenciBilgiSistemi.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.SectionClassroom;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.CourseService;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.SectionClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;
import com.araproje.OgrenciBilgiSistemi.service.StudentSectionService;
import com.araproje.OgrenciBilgiSistemi.util.ValidateMethods;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api/rest/admin/sections")
public class SectionRestController {

	@Autowired
	SectionService sectionService;
	@Autowired
	ClassroomService classroomService;
	@Autowired
	CourseService courseService;
	@Autowired
	InstructorService instructorService;
	@Autowired
	SectionClassroomService sectionClassroomService;
	@Autowired
	StudentSectionService studentSectionService;
	@Autowired
	ValidateMethods validateMethods;
	DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Map<String, Object> JSON){
		Course course;
		Section section;
		Set<SectionClassroom> sClasrooms = new HashSet<>();
		List<Map<String, String>> sectionDays;
		try {
			sectionDays = (List<Map<String, String>>)JSON.get("sectionClassrooms");
			course = courseService.get((String)JSON.get("courseCode"));
			if(validateMethods.validateSection(JSON)) {
				sectionService.create((String)JSON.get("sectionCode"), course, 
						instructorService.get((String)JSON.get("instructorCode")), (String)JSON.get("year"), 
						(String)JSON.get("term"), (int)JSON.get("quota"));
				
				for(Map<String, String> oneSectionDay : sectionDays) {
					sectionClassroomService.create(sectionService.get(courseService.get((String)JSON.get("courseCode")), (String)JSON.get("sectionCode")), classroomService.get(oneSectionDay.get("classroomCode")), 
							oneSectionDay.get("type"), oneSectionDay.get("startTime"), oneSectionDay.get("finishTime"), 
							oneSectionDay.get("day"));
					sClasrooms.add(new SectionClassroom(sectionService.get(courseService.get((String)JSON.get("courseCode")), (String)JSON.get("sectionCode")), classroomService.get(oneSectionDay.get("classroomCode")), 
							oneSectionDay.get("type"), oneSectionDay.get("startTime"), oneSectionDay.get("finishTime"), 
							oneSectionDay.get("day")));
				}
			}
			section = sectionService.get(courseService.get((String)JSON.get("courseCode")), (String)JSON.get("sectionCode"));
			section.setSectionClassrooms(sClasrooms);
		}catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(section);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id){
		try {
			sectionClassroomService.deleteAllWithGivenSection(sectionService.get(Integer.parseInt(id)));
			studentSectionService.deleteAllWithGivenSection(sectionService.get(Integer.parseInt(id)));
			sectionService.delete(Integer.parseInt(id));
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body("Deleted.");
	}
	
	@GetMapping
	public ResponseEntity<?> get(){
		List<Section> sections;
		try {
			sections = sectionService.getAll();
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable String id){
		Section section;
		try {
			section = sectionService.get(Integer.parseInt(id));
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(section);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAllFields(@PathVariable String id, @RequestBody Map<String, Object> JSON){
		List<Map<String, String>> sectionDays;
		Section section;
		Set<SectionClassroom> sClasrooms = new HashSet<>();
		try {
				section = sectionService.get(courseService.get((String)JSON.get("courseCode")), (String)JSON.get("sectionCode"));
				if(validateMethods.validateSectionUpdate(id, JSON, section)) {
					
					sectionDays = (List<Map<String, String>>)JSON.get("sectionClassrooms");
					section.setQuota((int)JSON.get("quota"));
					sectionService.update(section, Integer.parseInt(id));
					sectionClassroomService.deleteAllWithGivenSection(section);
					for(Map<String, String> oneSectionDay : sectionDays) {
						sectionClassroomService.create(sectionService.get(courseService.get((String)JSON.get("courseCode")), (String)JSON.get("sectionCode")), classroomService.get(oneSectionDay.get("classroomCode")), 
								oneSectionDay.get("type"), oneSectionDay.get("startTime"), oneSectionDay.get("finishTime"), 
								oneSectionDay.get("day"));
						sClasrooms.add(new SectionClassroom(sectionService.get(courseService.get((String)JSON.get("courseCode")), (String)JSON.get("sectionCode")), classroomService.get(oneSectionDay.get("classroomCode")), 
								oneSectionDay.get("type"), oneSectionDay.get("startTime"), oneSectionDay.get("finishTime"), 
								oneSectionDay.get("day")));
					}
					section.setSectionClassrooms(sClasrooms);
				}
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(section);
	}
	
}

