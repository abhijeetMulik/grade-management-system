package com.spring.dbsi.cassandra.dao;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class StudentGrade {

	@PrimaryKey
	private UUID id;
	private String firstName;
	private String lastName;
	private String grade;
	private String subject;

	public StudentGrade(UUID id, String firstName, String lastName, String grade, String subject) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.grade = grade;
		this.subject = subject;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "StudentGrade [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", grade=" + grade
				+ ", subject=" + subject + "]";
	}

}
