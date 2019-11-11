package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Course;

public interface CourseRepository extends CrudRepository<Course, Integer> {
	public Course findByCourseCode(String courseCode);
}
