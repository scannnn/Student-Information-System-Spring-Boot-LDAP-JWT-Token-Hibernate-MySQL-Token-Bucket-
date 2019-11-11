package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer>{
	public Student findByStudentCode(String studentCode);
}
