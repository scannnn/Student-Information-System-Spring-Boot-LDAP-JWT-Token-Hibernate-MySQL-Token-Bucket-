package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	public void create(String firstName, String lastName, String mail, String address, String phoneNumber, 
			String studentCode, Department department) {
		studentRepository.save(new Student(firstName, lastName, mail, address, phoneNumber, studentCode, department));
	}
	
	public void update(Student student) {
		studentRepository.save(student);
	}
	
	public void delete(String studentCode) {
		studentRepository.deleteById(studentRepository.findByStudentCode(studentCode).getId());
	}
	
	public Student get(String studentCode) {
		return studentRepository.findByStudentCode(studentCode);
	}
	
	public List<Student> getAll(){
		return (List<Student>)studentRepository.findAll();
	}
}
