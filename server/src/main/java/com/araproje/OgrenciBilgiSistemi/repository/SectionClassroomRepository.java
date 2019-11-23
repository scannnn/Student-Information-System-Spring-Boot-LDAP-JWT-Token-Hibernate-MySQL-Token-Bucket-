package com.araproje.OgrenciBilgiSistemi.repository;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Classroom;
import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.SectionClassroom;

public interface SectionClassroomRepository extends CrudRepository<SectionClassroom, Integer> {
	public SectionClassroom findBySectionAndClassroom(Section section, Classroom classroom);
}
