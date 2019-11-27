package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Course;
import com.araproje.OgrenciBilgiSistemi.model.Department;

public interface CourseRepository extends CrudRepository<Course, Integer> {
	public Course findByCourseCode(String courseCode);
	public Course findByTitle(String title);
	public Course findByDepartment(Department department);
}
