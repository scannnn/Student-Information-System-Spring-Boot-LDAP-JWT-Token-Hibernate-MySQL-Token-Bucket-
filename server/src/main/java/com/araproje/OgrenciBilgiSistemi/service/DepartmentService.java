package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Department;
import com.araproje.OgrenciBilgiSistemi.repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	public void create(String departmentCode, String title) {
		departmentRepository.save(new Department(departmentCode, title));
	}
	
	public void update(Department department) {
		departmentRepository.save(department);
	}
	// OPTIONAL GİRDİLER YOLLANABİLİR Mİ METHODA YA DA UPDATE İÇİN HEPSİ DOLU AL 
	public void delete(String departmentCode) {
		departmentRepository.deleteById(departmentRepository.findByDepartmentCode(departmentCode).getId());
	}
	
	public Department get(String departmentCode) {
		return departmentRepository.findByDepartmentCode(departmentCode);
	}
	
	public List<Department> getAll(){
		return (List<Department>) departmentRepository.findAll();
	}
}
