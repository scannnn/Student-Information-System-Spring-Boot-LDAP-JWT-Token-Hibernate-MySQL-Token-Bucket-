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
	@Column(name = "id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studentSection_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private StudentSection studentSection;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gradeType_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private GradeType gradeType;

	@Column (name = "grade")
	private long grade;
	
	public Grade() {}
	public Grade(StudentSection studentSection, GradeType gradeType, long grade) {
		super();
		this.studentSection = studentSection;
		this.gradeType = gradeType;
		this.grade = grade;
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
