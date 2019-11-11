package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.GradeType;

public interface GradeTypeRepository extends CrudRepository<GradeType, Integer> {
	public GradeType findByName(String name);
}
