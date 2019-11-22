package com.araproje.OgrenciBilgiSistemi.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.Classroom;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;

@RestController
@RequestMapping("/api/rest/admin/classrooms")
public class ClassroomRestController {

	@Autowired
	ClassroomService classroomService;
	
	@GetMapping
	public ResponseEntity<?> get(){
		List<Classroom> classrooms;
		try {
			classrooms = classroomService.getAll();
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(classrooms);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable String id){
		Classroom classroom;
		try {
			classroom = classroomService.get(Integer.parseInt(id));
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		return ResponseEntity
				.status(HttpStatus.OK).body(classroom);
	}
}
