package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Classroom;
import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.SectionClassroom;
import com.araproje.OgrenciBilgiSistemi.repository.SectionClassroomRepository;

@Service
public class SectionClassroomService {

	@Autowired
	SectionClassroomRepository sectionClassroomRepository;
	
	public void create(Section section, Classroom classroom, String type, String startDate, String finishDate, String day) {
		sectionClassroomRepository.save(new SectionClassroom(section, classroom, type, startDate, finishDate, day));
	}
	
	public void delete(Integer id) {
		sectionClassroomRepository.deleteById(id);
	}
	
	public void delete(Section section, Classroom classroom) {
		sectionClassroomRepository.delete(sectionClassroomRepository.findBySectionAndClassroom(section, classroom));
	}
	
	public void update(SectionClassroom sectionClassroom) {
		sectionClassroomRepository.save(sectionClassroom);
	}
	
	public SectionClassroom get(Section section, Classroom classroom) {
		return sectionClassroomRepository.findBySectionAndClassroom(section, classroom);
	}
	
	public SectionClassroom get(Integer id) {
		return sectionClassroomRepository.findById(id).get();
	}
	
	public List<SectionClassroom> getAll(){
		return (List<SectionClassroom>) sectionClassroomRepository.findAll();
	}
	
	public boolean isExist(Integer id) {
		return sectionClassroomRepository.existsById(id);
	}
}
