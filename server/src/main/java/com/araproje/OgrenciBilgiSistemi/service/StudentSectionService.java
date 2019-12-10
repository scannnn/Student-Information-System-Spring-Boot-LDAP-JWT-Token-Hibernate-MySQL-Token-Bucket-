package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.model.StudentSection;
import com.araproje.OgrenciBilgiSistemi.repository.StudentSectionRepository;

@Service
public class StudentSectionService {
	 
	@Autowired
	StudentSectionRepository studentSectionRepository;
	
	public void create(Student student, Section section) {
		studentSectionRepository.save(new StudentSection(student, section));
	}
	
	public void delete(Integer id) {
		studentSectionRepository.deleteById(id);
	}
	
	public void delete(Student student, Section section) {
		studentSectionRepository.deleteById(studentSectionRepository.findByStudentAndSection(student, section).getId());
	}
	
	public void deleteAllWithGivenSection(Section section) {
		List<StudentSection> studentSections = 
				(List<StudentSection>)studentSectionRepository.findAllStudentSectionsBySection(section);
		
		for(StudentSection ss : studentSections) {
			studentSectionRepository.delete(ss);
		}
	}
	
	public StudentSection get(Student student, Section section) {
		return studentSectionRepository.findByStudentAndSection(student, section);
	}
	
	public StudentSection get(Integer id) {
		return studentSectionRepository.findById(id).get();
	}
	
	public List<StudentSection> getAll(){
		return (List<StudentSection>) studentSectionRepository.findAll();
	}
	public List<StudentSection> getAll(Student student){
		return (List<StudentSection>) studentSectionRepository.findAllStudentSectionsByStudent(student);
	}
}
