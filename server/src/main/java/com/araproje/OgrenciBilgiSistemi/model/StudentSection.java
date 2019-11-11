package com.araproje.OgrenciBilgiSistemi.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_section")
public class StudentSection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_section_id")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
	private Student student;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
	private Section section;
	
	// ENROLL BAĞINTISI ÜZERİNDEN EKSTRA TUTMAK İSTEDİĞİMİZ ATTRIBUTELER BURAYA YAZILACAK
	
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
	
}
