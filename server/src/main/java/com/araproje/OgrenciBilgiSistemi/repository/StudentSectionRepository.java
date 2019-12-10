package com.araproje.OgrenciBilgiSistemi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.model.StudentSection;

public interface StudentSectionRepository extends CrudRepository<StudentSection, Integer>{
	public StudentSection findByStudentAndSection(Student student, Section section);
	public List<StudentSection> findAllStudentSectionsBySection(Section section);
	public List<StudentSection> findAllStudentSectionsByStudent(Student student);
}
