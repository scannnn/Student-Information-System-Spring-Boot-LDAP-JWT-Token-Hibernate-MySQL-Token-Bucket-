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
	
	@Column(name = "instructorCode", nullable = false, updatable = true)
    private String instructorCode;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Department department;

	public Instructor() {}
	public Instructor(String instructorCode, Department department) {
		super();
		this.instructorCode = instructorCode;
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstructorCode() {
		return instructorCode;
	}

	public void setInstructorCode(String instructorCode) {
		this.instructorCode = instructorCode;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
