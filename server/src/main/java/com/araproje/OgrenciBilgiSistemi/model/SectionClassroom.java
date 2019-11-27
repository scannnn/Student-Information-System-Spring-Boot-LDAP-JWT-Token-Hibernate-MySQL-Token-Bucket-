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
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "startTime", nullable = false, updatable = true)
	private String startTime;
	
	@Column(name = "finishTime", nullable = false, updatable = true)
	private String finishTime;
	
	public SectionClassroom() {}

	public SectionClassroom(Section section, Classroom classroom, String type, String startTime, String finishTime, String day) {
		super();
		this.section = section;
		this.classroom = classroom;
		this.type = type;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.day = day;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
}
