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
@Table(name="students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private int id;
	
	// AYNI ŞEKİLDE ÖĞRENCİ BİLGİLERİ DE TUTULSUN
	
	@Column(name = "studentCode", nullable = false, updatable = true)
    private String studentCode;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Department department;
	
	@OneToMany(mappedBy = "student")
	private Set<StudentSection> studentSections = new HashSet<StudentSection>();

	public Student() {}
	public Student(String studentCode, Department department) {
		super();
		this.studentCode = studentCode;
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<StudentSection> getStudentSections() {
		return studentSections;
	}

	public void setStudentSections(Set<StudentSection> studentSections) {
		this.studentSections = studentSections;
	}
	
}
