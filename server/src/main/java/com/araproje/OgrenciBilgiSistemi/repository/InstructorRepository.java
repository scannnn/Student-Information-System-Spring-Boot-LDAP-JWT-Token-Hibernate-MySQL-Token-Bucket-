package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Integer>{
	public Instructor findByInstructorCode(String instructorCode);
}
