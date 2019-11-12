package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Grade;
import com.araproje.OgrenciBilgiSistemi.model.GradeType;
import com.araproje.OgrenciBilgiSistemi.model.StudentSection;

public interface GradeRepository extends CrudRepository<Grade, Integer>{
	public Grade findByStudentSectionAndGradeType(StudentSection studentSection, GradeType gradeType);
}
