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
@Table(name="instructors")
public class Instructor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private int id;
	
	// OGRETMEN BİLGİLERİ TUTULACAKSA YAZ ONLARI DA
	// BENCE TUTULMALI AMA ŞUAN ÜŞENDİM SALI TUTALIM (BU LDAP DAKİ ŞİFRE VE KULLANICI ADI HARİÇ)
	
	@Column(name = "instructorId", nullable = false, updatable = true)
    private String instructorId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Department department;

	public Instructor(int id, String instructorId, Department department) {
		super();
		this.id = id;
		this.instructorId = instructorId;
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
