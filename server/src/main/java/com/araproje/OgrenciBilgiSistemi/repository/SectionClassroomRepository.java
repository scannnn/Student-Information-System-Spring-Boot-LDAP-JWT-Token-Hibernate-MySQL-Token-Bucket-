package com.araproje.OgrenciBilgiSistemi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.araproje.OgrenciBilgiSistemi.model.Classroom;
import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.SectionClassroom;

public interface SectionClassroomRepository extends CrudRepository<SectionClassroom, Integer> {
	public SectionClassroom findBySectionAndClassroom(Section section, Classroom classroom);
	public List<SectionClassroom> findAllSectionClassroomsBySection(Section section);
}
