package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.model.Instructor;
import com.araproje.OgrenciBilgiSistemi.repository.InstructorRepository;

@Service
public class InstructorService {

	@Autowired
	InstructorRepository instructorRepository;
	
	public void create(String instructorCode, Department department) {
		instructorRepository.save(new Instructor(instructorCode, department));
	}
	
	public void update(Instructor instructor) {
		instructorRepository.save(instructor);
	}
	
	public void delete(String instructorCode) {
		instructorRepository.deleteById(instructorRepository.findByInstructorCode(instructorCode).getId());
	}
	
	public Instructor get(String instructorCode) {
		return instructorRepository.findByInstructorCode(instructorCode);
	}
	
	public List<Instructor> getAll(){
		return (List<Instructor>) instructorRepository.findAll();
	}
}
