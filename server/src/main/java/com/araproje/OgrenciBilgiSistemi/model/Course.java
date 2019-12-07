package com.araproje.OgrenciBilgiSistemi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Column(name = "credit")
	private int credit;
	
	@Column(name = "language")
	private String language;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Department department;
    
	@ManyToMany
	@JoinTable(name = "course_prerequisites", joinColumns = {
            @JoinColumn(name = "whosprerequisites", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "prerequisites", referencedColumnName = "id", nullable = false)})
	private List<Course> prerequisites;
	
	@ManyToMany(mappedBy = "prerequisites")
	private List<Course> whosprerequisites;

	public Course() {}

	public Course(String courseCode, String title, Department department, int credit, String language, List<Course> prerequisites) {
		super();
		this.courseCode = courseCode;
		this.title = title;
		this.department = department;
		this.credit = credit;
		this.language = language;
		this.prerequisites = prerequisites;
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

	public List<Map<String, String>> getPrerequisites() {
		List<Map<String, String>> prereqs = new ArrayList<>();
		for(Course oneCourse : prerequisites) {
			Map<String, String> temp = new HashMap<>();
			temp.put("courseCode", oneCourse.getCourseCode());
			temp.put("title", oneCourse.getTitle());
			prereqs.add(temp);
		}
		return prereqs;
	}
	
	public void setPrerequisites(List<Course> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public List<Map<String, String>> getWhosprerequisites() {
		List<Map<String, String>> whosprereqs;
		if(whosprerequisites != null) {
			whosprereqs = new ArrayList<>();
			for(Course oneCourse : whosprerequisites) {
				Map<String, String> temp = new HashMap<>();
				temp.put("courseCode", oneCourse.getCourseCode());
				temp.put("title", oneCourse.getTitle());
				whosprereqs.add(temp);
			}
			return whosprereqs;
		}
		else {
			whosprereqs = null;
			return whosprereqs;
		}
		
	}

	public void setWhosprerequisites(List<Course> whosprerequisites) {
		this.whosprerequisites = whosprerequisites;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
	
}
