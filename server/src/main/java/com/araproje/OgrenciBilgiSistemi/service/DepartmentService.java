package com.araproje.OgrenciBilgiSistemi.service;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
	
	public void updateParticial(Department department, Integer id) {
		Department persistedDepartment = departmentRepository.findById(id).get();
		   String[] ignoredProperties = getNullPropertyNames(department);
		   BeanUtils.copyProperties(department, persistedDepartment, ignoredProperties);
		   departmentRepository.save(department);
	}

	public void delete(Integer id) {
		departmentRepository.deleteById(id);
	}
	
	public Department get(Integer id) {
		return departmentRepository.findById(id).get();
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
	
	private String[] getNullPropertyNames(Department department) {
		   final BeanWrapper wrappedSource = new BeanWrapperImpl(department);
		   return Stream.of(wrappedSource.getPropertyDescriptors())
		            .map(FeatureDescriptor::getName)
		            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
		            .toArray(String[]::new);
		}
}
