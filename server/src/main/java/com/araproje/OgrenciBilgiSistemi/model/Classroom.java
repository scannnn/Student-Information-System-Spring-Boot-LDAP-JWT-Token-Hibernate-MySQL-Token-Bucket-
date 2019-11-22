package com.araproje.OgrenciBilgiSistemi.model;

import java.util.HashSet;
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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="classrooms")
public class Classroom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private int id;
	
	@Column(name = "classroomCode", nullable = false, updatable = true)
    private String classroomCode;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Department department;
	
	@OneToMany(mappedBy = "classroom")
	private Set<SectionClassroom> sectionClassrooms = new HashSet<SectionClassroom>();

	public Classroom() {}
	public Classroom(String classroomCode, Department department) {
		super();
		this.classroomCode = classroomCode;
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassroomCode() {
		return classroomCode;
	}

	public void setClassroomCode(String classroomCode) {
		this.classroomCode = classroomCode;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<SectionClassroom> getSectionClassrooms() {
		return sectionClassrooms;
	}

	public void setSectionClassrooms(Set<SectionClassroom> sectionClassrooms) {
		this.sectionClassrooms = sectionClassrooms;
	}
	
}
