package com.araproje.OgrenciBilgiSistemi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Classroom;
import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.repository.ClassroomRepository;
import com.araproje.OgrenciBilgiSistemi.repository.DepartmentRepository;

@Service
public class ClassroomService {

	@Autowired
	ClassroomRepository classroomRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	
	public void create(String classroomCode, String departmentCode) {
		Department temp = departmentRepository.findByDepartmentCode(departmentCode);
		System.out.println("TEMP = "+temp.toString());
		classroomRepository.save(new Classroom(classroomCode, temp));
	}
}
