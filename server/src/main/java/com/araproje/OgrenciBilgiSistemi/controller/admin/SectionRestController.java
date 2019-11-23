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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.SectionClassroom;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.CourseService;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.SectionClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;

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
	
	
	// SECTION OLUŞTURMA AYRI VE SECTIONLARA SINIF VE SAAT ATAMA AYRI MI OLACAK YOKSA AYNI MI OLACAK SOR.
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Map<String, Object> JSON){
		try {
			sectionService.create((String)JSON.get("sectionCode"), courseService.get((String)JSON.get("courseCode")), 
					instructorService.get((String)JSON.get("instructorCode")));
			sectionClassroomService.create(sectionService.get((String)JSON.get("sectionCode")), 
					classroomService.get((String)JSON.get("classroomCode")), (Date)sourceFormat.parse((String)JSON.get("startDate")),
					(Date)sourceFormat.parse((String)JSON.get("finishDate"))); // YUKARIDAKİ SORUYA GÖRE BURASI OLACAK MI OLMAYACAK MI SOR???
		}catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Added.");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id){
		try {
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
		try {
			if(sectionService.isExist(Integer.parseInt(id))) {
				sectionService.update(new Section((String)JSON.get("sectionCode"), courseService.get((String)JSON.get("courseCode")), 
						instructorService.get((String)JSON.get("instructorCode"))), Integer.parseInt(id));
				sectionClassroomService.update(new SectionClassroom(sectionService.get((String)JSON.get("sectionCode")), 
						classroomService.get((String)JSON.get("classroomCode")),(Date)sourceFormat.parse((String)JSON.get("startDate")),
						(Date)sourceFormat.parse((String)JSON.get("finishDate"))));  // SOR
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
	}
}
