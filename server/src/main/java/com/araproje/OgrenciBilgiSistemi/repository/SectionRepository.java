package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Section;

public interface SectionRepository extends CrudRepository<Section, Integer>{
	public Section findBySectionCode(String sectionCode);
}
