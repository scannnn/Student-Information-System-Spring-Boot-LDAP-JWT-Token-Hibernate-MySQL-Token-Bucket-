package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Grade;
import com.araproje.OgrenciBilgiSistemi.model.GradeType;

public interface GradeRepository extends CrudRepository<Grade, Integer>{
	public Grade findByGradeType(GradeType gradeType);
}
