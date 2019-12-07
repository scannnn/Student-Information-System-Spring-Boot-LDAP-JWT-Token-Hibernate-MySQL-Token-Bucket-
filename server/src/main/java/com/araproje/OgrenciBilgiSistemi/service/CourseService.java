package com.araproje.OgrenciBilgiSistemi.service;

import java.util.ArrayList;
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
	
	public void create(String courseCode, String title, Department department, int credit, String language,
			List<Course> prerequisites) {
		courseRepository.save(new Course(courseCode, title, department, credit, language, prerequisites));
	}
	
	public void update(Course course, Integer id) {
		course.setId(id);
		courseRepository.save(course);
	}
	
	public void delete(Integer id) {
		courseRepository.deleteById(id);
	}
	
	public void delete(String courseCode) {
		courseRepository.deleteById(courseRepository.findByCourseCode(courseCode).getId());
	}
	
	public Course get(String courseCode) {
		return courseRepository.findByCourseCode(courseCode);
	}
	
	public Course getWithTitle(String title) {
		return courseRepository.findByTitle(title);
	}
	
	public Course get(Integer id) {
		return courseRepository.findById(id).get();
	}
	
	public List<Course> getAll(Department department) {
		List<Course> courses = (List<Course>)courseRepository.findAll();
		List<Course> coursesWithGivenDept = new ArrayList<>();
		for(Course c : courses) {
			if(c.getDepartment().getId() == department.getId()) {
				coursesWithGivenDept.add(c);
			}
		}
		return coursesWithGivenDept;
	}
	
	public List<Course> getAll(){
		return (List<Course>)courseRepository.findAll();
	}
	
	public boolean isExist(Integer id) {
		return courseRepository.existsById(id);
	}
	
	public boolean isExist(String courseCode) {
		if(courseRepository.findByCourseCode(courseCode) == null) return false;
		else return true;
	}
}
