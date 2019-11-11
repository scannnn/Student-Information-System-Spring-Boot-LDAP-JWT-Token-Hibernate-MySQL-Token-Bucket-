package com.araproje.OgrenciBilgiSistemi.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "section_classroom")
public class SectionClassroom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "section_classroom_id")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
	private Section section;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "classroom_id")
	private Classroom classroom;
	
	@Column(name = "startDate", nullable = false, updatable = true)
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "finishDate", nullable = false, updatable = true)
	@Temporal(TemporalType.DATE)
	private Date finishDate;
	
	public SectionClassroom() {}
	public SectionClassroom(Section section, Classroom classroom, Date startDate, Date finishDate) {
		super();
		this.section = section;
		this.classroom = classroom;
		this.startDate = startDate;
		this.finishDate = finishDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
}
