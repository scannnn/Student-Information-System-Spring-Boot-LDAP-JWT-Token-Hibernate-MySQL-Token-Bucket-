package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Classroom;

public interface ClassroomRepository extends CrudRepository<Classroom, Integer>{
	public Classroom findByClassroomCode(String classroomCode);
}
