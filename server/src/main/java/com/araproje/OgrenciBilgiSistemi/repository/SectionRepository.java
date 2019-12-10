package com.araproje.OgrenciBilgiSistemi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Course;
import com.araproje.OgrenciBilgiSistemi.model.Section;

public interface SectionRepository extends CrudRepository<Section, Integer>{
	public Section findBySectionCode(String sectionCode);
	public Section findByCourseAndSectionCode(Course course, String sectionCode);
	public List<Section> findAllSectionsByYearAndTerm(String year, String term);
}
