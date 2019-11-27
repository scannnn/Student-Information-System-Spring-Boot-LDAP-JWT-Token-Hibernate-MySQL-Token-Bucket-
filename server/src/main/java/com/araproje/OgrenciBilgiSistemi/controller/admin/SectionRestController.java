package com.araproje.OgrenciBilgiSistemi.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.CourseService;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.SectionClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;

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
	DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Map<String, Object> JSON){
		try {
			List<Map<String, String>> sectionDays = (List<Map<String, String>>)JSON.get("sectionClassrooms");
			if(sectionDays != null) {
				sectionService.create((String)JSON.get("sectionCode"), courseService.get((String)JSON.get("courseCode")), 
						instructorService.get((String)JSON.get("instructorCode")), (Date)sourceFormat.parse((String)JSON.get("startDate")), 
						(Date)sourceFormat.parse((String)JSON.get("finishDate")));
				
				for(Map<String, String> oneSectionDay : sectionDays) {
					sectionClassroomService.create(sectionService.get(courseService.get((String)JSON.get("courseCode")), (String)JSON.get("sectionCode")), classroomService.get(oneSectionDay.get("classroomCode")), 
							oneSectionDay.get("type"), oneSectionDay.get("startDate"), oneSectionDay.get("finishDate"), 
							oneSectionDay.get("day"));
				}
			}
			else {
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("sectionDays is empty.");
			}
			
		}catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(sectionService.get(courseService.get((String)JSON.get("courseCode")), (String)JSON.get("sectionCode")));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id){
		try {
			sectionClassroomService.deleteAllWithGivenSection(sectionService.get(Integer.parseInt(id)));
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
	
	// YUKARIDAKİ ADD METODUNA BAKARAK SECTION CLASSLARI 0 LAYIP DEGİSTİREREK PUT MAPPING I YAZ
	/*@PutMapping("/{id}")
	public ResponseEntity<?> updateAllFields(@PathVariable String id, @RequestBody Map<String, Object> JSON){
		try {
			if(sectionService.isExist(Integer.parseInt(id))) {
				sectionService.create((String)JSON.get("sectionCode"), courseService.get((String)JSON.get("courseCode")), 
						instructorService.get((String)JSON.get("instructorCode")), (Date)sourceFormat.parse((String)JSON.get("startDate")), 
						(Date)sourceFormat.parse((String)JSON.get("finishDate")));
			
			}
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body("Updated.");
	}*/
}
