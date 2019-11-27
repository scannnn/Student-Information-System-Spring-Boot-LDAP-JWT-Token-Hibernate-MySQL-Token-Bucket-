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
	
	public void update(Department department, Integer id) {
		department.setId(id);
		departmentRepository.save(department);
	}

	public void delete(Integer id) {
		departmentRepository.deleteById(id);
	}
	
	public Department get(Integer id) {
		return departmentRepository.findById(id).get();
	}
	
	public Department getWithTitle(String title) {
		return departmentRepository.findByTitle(title);
	}
	
	public void delete(String departmentCode) {
		departmentRepository.deleteById(departmentRepository.findByDepartmentCode(departmentCode).getId());
	}
	
	public Department get(String departmentCode) {
		return departmentRepository.findByDepartmentCode(departmentCode);
	}
	
	public List<Department> getAll(){
		return (List<Department>) departmentRepository.findAll();
	}
	
	public boolean isExist(Integer id) {
		return departmentRepository.existsById(id);
	}
	
}
