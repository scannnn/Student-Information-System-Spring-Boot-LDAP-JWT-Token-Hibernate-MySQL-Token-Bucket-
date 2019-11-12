package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Course;
import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	CourseRepository courseRepository;
	
	public void create(String courseCode, String title, Department department) {
		courseRepository.save(new Course(courseCode, title, department));
	}
	
	public void update(Course course) {
		courseRepository.save(course);
	}
	
	public void delete(String courseCode) {
		courseRepository.deleteById(courseRepository.findByCourseCode(courseCode).getId());
	}
	// DELETE İŞLEMLERİ GÜVENLİK ACISINDAN DB ID' Sİ İLE YAPILSIN MI SOR
	public Course get(String courseCode) {
		return courseRepository.findByCourseCode(courseCode);
	}
	
	public List<Course> getAll(){
		return (List<Course>)courseRepository.findAll();
	}
}
