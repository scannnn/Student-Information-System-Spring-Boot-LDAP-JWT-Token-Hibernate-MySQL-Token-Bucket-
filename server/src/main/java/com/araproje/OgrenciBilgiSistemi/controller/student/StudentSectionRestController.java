package com.araproje.OgrenciBilgiSistemi.controller.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.model.StudentSection;
import com.araproje.OgrenciBilgiSistemi.payload.SectionLastPick;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;
import com.araproje.OgrenciBilgiSistemi.service.StudentSectionService;
import com.araproje.OgrenciBilgiSistemi.service.StudentService;
import com.araproje.OgrenciBilgiSistemi.util.ValidateMethods;

@RestController
@RequestMapping("/api/rest/student/sections")
public class StudentSectionRestController {
	
	@Autowired
	StudentService studentService;
	@Autowired
	SectionService sectionService;
	@Autowired
	StudentSectionService studentSectionService;
	@Autowired
	ValidateMethods validateMethods;
	
	Map<String, SectionLastPick> studentList = new ConcurrentHashMap<>();
	
	@PostMapping("/{sectionId}")
	public ResponseEntity<?> add(@PathVariable String sectionId, HttpServletRequest request){
		Student student;
		Section section;
		try {
			student = studentService.get((String)request.getAttribute("uid"));
			section = sectionService.get(Integer.parseInt(sectionId));
			if(validateMethods.validateStudentSection(student, sectionId)) {
				if(studentList.containsKey(student.getStudentCode()+""+section.getCourse().getCourseCode())) {
					SectionLastPick temp = studentList.get(student.getStudentCode()+""+section.getCourse().getCourseCode());
					if(temp.chechForRePick()) {
						studentList.remove(student.getStudentCode()+""+section.getCourse().getCourseCode());
					}
					else {
						throw new Exception("Eklemeye çalıştığınız dersi en son "
								+ studentList.get(student.getStudentCode()+""+section.getCourse().getCourseCode()).getLastPick()+ " eklediniz. Yeniden aynı derse"
								+ " ekleme yapabilmek için üzerinden 5 dakika geçmesi gerekmektedir. ");
					}
				}
				else {
					section.increaseStudentCount();
					sectionService.update(section, Integer.parseInt(sectionId));
					studentSectionService.create(student, section);
					SectionLastPick temp = new SectionLastPick(student.getStudentCode(), section.getCourse().getCourseCode());
					studentList.put(student.getStudentCode()+""+section.getCourse().getCourseCode(), temp);
				}
				
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
		List<StudentSection> studentSections = studentSectionService.getAll(student);
		List<StudentSection> studentSectionsTemp = studentSections;
		for(StudentSection ss : studentSectionsTemp) {
			if(!ss.getSection().getYear().equalsIgnoreCase(section.getYear()) && 
					!ss.getSection().getTerm().equalsIgnoreCase(section.getTerm()) ) {
				studentSections.remove(ss);
			}
		}
		
		return ResponseEntity
				.status(HttpStatus.OK).body(section);
	}
	
	@DeleteMapping("/{sectionId}")
	public ResponseEntity<?> delete(@PathVariable String sectionId, HttpServletRequest request){
		Section section;
		Student student;
		try {
			student = studentService.get((String)request.getAttribute("uid"));
			section = sectionService.get(Integer.parseInt(sectionId));
			section.decreaseStudentCount();
			sectionService.update(section, Integer.parseInt(sectionId));
			studentSectionService.delete(studentSectionService.get(student, section).getId());
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body("Deleted.");
	}
	
	@GetMapping("/{year}/{term}")
	public ResponseEntity<?> getAll(@PathVariable String year, @PathVariable String term, HttpServletRequest request){
		Student student;
		List<Section> sections;
		List<StudentSection> studentSections;
		try {
			student = studentService.get((String)request.getAttribute("uid"));
			studentSections = studentSectionService.getAll(student);
			sections = new ArrayList<>();
			for(StudentSection ss : studentSections) {
				if(ss.getSection().getYear().equalsIgnoreCase(year) && 
						ss.getSection().getYear().equalsIgnoreCase(year)) {
					sections.add(ss.getSection());
				}
			}
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(sections);
	}
	
	@GetMapping("/common/{year}/{term}")
	public ResponseEntity<?> getByYearAndTerm(@PathVariable String year, @PathVariable String term, 
			@RequestParam(name = "query") String query){
		List<Section> sections;
		List<Section> sectionsSend = new ArrayList<>();
		try {
			sections = sectionService.getByYearAndTerm(year, term);
			for(Section s : sections) {
				if(s.getCourse().getCourseCode().contains(query) ||
						s.getCourse().getTitle().contains(query)) {
					sectionsSend.add(s);
				}
			}
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(sectionsSend);
	}
	
	@GetMapping()
	public ResponseEntity<?> getAll(HttpServletRequest request){
		Student student;
		List<Section> sections;
		List<StudentSection> studentSections;
		try {
			student = studentService.get((String)request.getAttribute("uid"));
			studentSections = studentSectionService.getAll(student);
			sections = new ArrayList<>();
			for(StudentSection ss : studentSections) {
					sections.add(ss.getSection());
			}
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
