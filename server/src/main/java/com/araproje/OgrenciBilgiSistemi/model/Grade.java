package com.araproje.OgrenciBilgiSistemi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "grades")
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grade_id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studentSection_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private StudentSection studentSection;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gradeType_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private GradeType gradeType;

	public Grade(int id, StudentSection studentSection, GradeType gradeType) {
		super();
		this.id = id;
		this.studentSection = studentSection;
		this.gradeType = gradeType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StudentSection getStudentSection() {
		return studentSection;
	}

	public void setStudentSection(StudentSection studentSection) {
		this.studentSection = studentSection;
	}

	public GradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}

}
