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
@Table(name="sections")
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private int id;
	
	@Column(name = "sectionCode", nullable = false, updatable = true)
    private String sectionCode;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Course course;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Instructor instructor;
	
	@OneToMany(mappedBy = "section")
	private Set<SectionClassroom> sectionClassrooms = new HashSet<SectionClassroom>();
	
	@OneToMany(mappedBy = "section")
	private Set<StudentSection> studentSections = new HashSet<StudentSection>();

	public Section(int id, String sectionCode, Course course, Instructor instructor) {
		super();
		this.id = id;
		this.sectionCode = sectionCode;
		this.course = course;
		this.instructor = instructor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Set<SectionClassroom> getSectionClassrooms() {
		return sectionClassrooms;
	}

	public void setSectionClassrooms(Set<SectionClassroom> sectionClassrooms) {
		this.sectionClassrooms = sectionClassrooms;
	}

	public Set<StudentSection> getStudentSections() {
		return studentSections;
	}

	public void setStudentSections(Set<StudentSection> studentSections) {
		this.studentSections = studentSections;
	}
	
}
