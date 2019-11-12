package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Course;
import com.araproje.OgrenciBilgiSistemi.model.Instructor;
import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.repository.SectionRepository;

@Service
public class SectionService {

	@Autowired
	SectionRepository sectionRepository;
	
	public void create(String sectionCode, Course course, Instructor instructor) {
		sectionRepository.save(new Section(sectionCode, course, instructor));
	}
	
	public void update(Section section) {
		sectionRepository.save(section);
	}
	
	public void delete(String sectionCode) {
		sectionRepository.deleteById(sectionRepository.findBySectionCode(sectionCode).getId());
	}
	
	public Section get(String sectionCode) {
		return sectionRepository.findBySectionCode(sectionCode);
	}
	
	public List<Section> getAll(){
		return (List<Section>)sectionRepository.findAll();
	}
}
