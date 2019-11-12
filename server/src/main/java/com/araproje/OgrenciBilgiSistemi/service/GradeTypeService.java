package com.araproje.OgrenciBilgiSistemi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.GradeType;
import com.araproje.OgrenciBilgiSistemi.repository.GradeTypeRepository;

@Service
public class GradeTypeService {
	
	@Autowired
	GradeTypeRepository gradeTypeRepository;
	
	public void create(String name) {
		gradeTypeRepository.save(new GradeType(name));
	}
	
	public void update(GradeType gradeType) {
		gradeTypeRepository.save(gradeType);
	}
	
	public void delete(String name) {
		gradeTypeRepository.deleteById(gradeTypeRepository.findByName(name).getId());
	}
	
	public GradeType get(String name) {
		return gradeTypeRepository.findByName(name);
	}
	
	public List<GradeType> getAll(){
		return (List<GradeType>)gradeTypeRepository.findAll();
	}
}
