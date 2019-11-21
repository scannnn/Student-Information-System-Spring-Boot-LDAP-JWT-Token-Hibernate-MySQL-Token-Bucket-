package com.araproje.OgrenciBilgiSistemi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private int id;
	
	@Column (name = "courseCode", nullable = false, updatable = true)
	private String courseCode;
	
	@Column(name = "title")
    private String title;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Department department;
    
	@ManyToMany
	@JoinTable(name = "course_prerequisities", joinColumns = {
            @JoinColumn(name = "prerequisities", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "whosprerequisities", referencedColumnName = "id", nullable = false)})
	private List<Course> prerequisities;
	
	@ManyToMany(mappedBy = "prerequisities")
	private List<Course> whosprerequisities;

	public Course() {}

	public Course(String courseCode, String title, Department department) {
		super();
		this.courseCode = courseCode;
		this.title = title;
		this.department = department;
		prerequisities = new ArrayList<Course>();
		whosprerequisities = new ArrayList<Course>();
	}

	public Course(String courseCode, String title, Department department, List<Course> prerequisities) {
		super();
		this.courseCode = courseCode;
		this.title = title;
		this.department = department;
		this.prerequisities = prerequisities;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Course> getPrerequisities() {
		return prerequisities;
	}

	public void setPrerequisities(List<Course> prerequisities) {
		this.prerequisities = prerequisities;
	}

	public List<Course> getWhosprerequisities() {
		return whosprerequisities;
	}

	public void setWhosprerequisities(List<Course> whosprerequisities) {
		this.whosprerequisities = whosprerequisities;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
}
