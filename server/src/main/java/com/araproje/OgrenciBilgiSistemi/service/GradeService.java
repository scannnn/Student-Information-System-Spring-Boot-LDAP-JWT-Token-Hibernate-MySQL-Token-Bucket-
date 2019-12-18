package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Grade;
import com.araproje.OgrenciBilgiSistemi.model.GradeType;
import com.araproje.OgrenciBilgiSistemi.model.StudentSection;
import com.araproje.OgrenciBilgiSistemi.repository.GradeRepository;

@Service
public class GradeService {
	
	@Autowired
	GradeRepository gradeRepository;
	
	public void create(StudentSection studentSection, GradeType gradeType, int grade) {
		gradeRepository.save(new Grade(studentSection, gradeType, grade));
	}
	
	public void update(Grade grade) {
		gradeRepository.save(grade);
	}
	
	public void delete(StudentSection studentSection, GradeType gradeType) {
		gradeRepository.deleteById(gradeRepository.findByStudentSectionAndGradeType(studentSection, gradeType).getId());
	}
	
	public Grade get(StudentSection studentSection, GradeType gradeType) {
		return gradeRepository.findByStudentSectionAndGradeType(studentSection, gradeType);
	}
	
	public List<Grade> getAll() {
		return (List<Grade>)gradeRepository.findAll();
	}
	
	public boolean isExist(StudentSection studentSection, GradeType gradeType) {
		System.out.println("Hello inside");
		if(gradeRepository.findByStudentSectionAndGradeType(studentSection, gradeType) == null) {
			return false;
		}
		else return true;
	}
}
