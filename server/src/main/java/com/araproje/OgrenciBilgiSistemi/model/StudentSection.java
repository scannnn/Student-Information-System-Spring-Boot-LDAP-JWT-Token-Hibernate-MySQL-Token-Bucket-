package com.araproje.OgrenciBilgiSistemi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "student_section")
public class StudentSection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_section_id")
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id")
	private Student student;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "section_id")
	private Section section;
	
	@OneToMany(mappedBy = "studentSection")
	private Set<Grade> grades = new HashSet<Grade>();
	
	public StudentSection() {}
	public StudentSection(Student student, Section section) {
		super();
		this.student = student;
		this.section = section;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@JsonIgnore
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
	public Map<String, Map<String, Object>> getGrades() {
		Map<String, Map<String, Object>> response = new HashMap<>();
		for(Grade g : grades) {
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("id", g.getId());
			temp.put("grade", g.getGrade());
			response.put(g.getGradeType().getName(), temp);
		}
		return response;
	}
	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	
}
