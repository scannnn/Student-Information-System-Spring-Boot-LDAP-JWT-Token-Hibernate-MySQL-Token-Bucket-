package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Classroom;
import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.repository.ClassroomRepository;

@Service
public class ClassroomService {

	@Autowired
	ClassroomRepository classroomRepository;
	
	public void create(String classroomCode, Department department) {
		classroomRepository.save(new Classroom(classroomCode, department));
	}
	
	public void update(Classroom classroom) {
		classroomRepository.save(classroom);
	}
	
	public void delete(String classroomCode) {
		classroomRepository.deleteById(classroomRepository.findByClassroomCode(classroomCode).getId()); // :(
	}
	
	public Classroom get(String classroomCode) {
		return classroomRepository.findByClassroomCode(classroomCode);
	}
	
	public Classroom get(Integer id) {
		return classroomRepository.findById(id).get();
	}
	
	public List<Classroom> getAll() {
		return (List<Classroom>)classroomRepository.findAll();
	}
}
