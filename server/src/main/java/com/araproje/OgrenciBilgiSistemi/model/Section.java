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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="sections")
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private int id;
	
	@Column(name = "sectionCode", nullable = false, updatable = true)
    private String sectionCode;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Course course;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Instructor instructor;
	
	@Column(name = "year", nullable = false, updatable = true)
	private String year;
	
	@Column(name = "term", nullable = false, updatable = true)
	private String term;
	
	@Column(name = "quota", nullable = false, updatable = true)
	private int quota;
	
	@Column(name = "studentCount", updatable = true)
	private int studentCount;
	
	@OneToMany(mappedBy = "section")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<SectionClassroom> sectionClassrooms = new HashSet<SectionClassroom>();
	
	@OneToMany(mappedBy = "section")
	private Set<StudentSection> studentSections = new HashSet<StudentSection>();

	public Section() {}

	public Section(String sectionCode, Course course, Instructor instructor, String year, String term, Integer quota) {
		super();
		this.sectionCode = sectionCode;
		this.course = course;
		this.instructor = instructor;
		this.year = year;
		this.term = term;
		this.quota = quota;
		this.studentCount = 0;
	}

	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
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

	public List<Map<String, String>> getSectionClassrooms() {
		List<Map<String, String>> sectClassrooms = new ArrayList<>();
		
		for(SectionClassroom oneSecClass : sectionClassrooms) {
			Map<String, String> temp = new HashMap<>();
			temp.put("id", ""+oneSecClass.getId());
			temp.put("classroomCode", oneSecClass.getClassroom().getClassroomCode());
			temp.put("type", oneSecClass.getType());
			temp.put("startTime",oneSecClass.getStartTime());
			temp.put("finishTime", oneSecClass.getFinishTime());
			temp.put("day", oneSecClass.getDay());
			sectClassrooms.add(temp);
		}
		return sectClassrooms;
	}
	
	@JsonIgnore
	public List<SectionClassroom> getSecClassrooms(){
		List<SectionClassroom> sc = new ArrayList<>();
		sc.addAll(sectionClassrooms);
		return sc;
	}
	public void setSectionClassrooms(Set<SectionClassroom> sectionClassrooms) {
		this.sectionClassrooms = sectionClassrooms;
	}

	public Set<StudentSection> getStudentSections() {
		return null;
	}

	public void setStudentSections(Set<StudentSection> studentSections) {
		this.studentSections = studentSections;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

	public int getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}
	
	public void increaseStudentCount() {
		studentCount++;
	}
	
	public void decreaseStudentCount() {
		studentCount--;
	}
	
	public boolean isSectionFull() {
		if(quota == studentCount) return true;
		else return false;
	}
}
