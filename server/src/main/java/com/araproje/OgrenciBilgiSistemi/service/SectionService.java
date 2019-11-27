package com.araproje.OgrenciBilgiSistemi.service;

import java.util.Date;
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
	
	public void create(String sectionCode, Course course, Instructor instructor, Date startDate, Date finishDate) {
		sectionRepository.save(new Section(sectionCode, course, instructor, startDate, finishDate));
	}
	
	public void update(Section section, Integer id) {
		section.setId(id);
		sectionRepository.save(section);
	}
	
	public void delete(String sectionCode) {
		sectionRepository.deleteById(sectionRepository.findBySectionCode(sectionCode).getId());
	}
	
	public void delete(Integer id) {
		sectionRepository.deleteById(id);
	}
	
	public Section get(Course course, String sectionCode) {
		return sectionRepository.findByCourseAndSectionCode(course, sectionCode);
	}
	
	public Section get(Integer id) {
		return sectionRepository.findById(id).get();
	}
	
	public List<Section> getAll(){
		return (List<Section>)sectionRepository.findAll();
	}
	
	public boolean isExist(Integer id) {
		return sectionRepository.existsById(id);
	}
}
