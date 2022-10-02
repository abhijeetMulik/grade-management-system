package com.spring.dbsi.cassandra.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.utils.UUIDs;
import com.spring.dbsi.cassandra.dao.StudentGrade;
import com.spring.dbsi.cassandra.repo.DataRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class GradeRestController {

	@Autowired
	DataRepository dataRepository;

	@PostMapping("/insert")
	public ResponseEntity<StudentGrade> insertStudentGrade(@RequestBody StudentGrade studentGrade) {
		try {
			StudentGrade sg = dataRepository.save(new StudentGrade(UUIDs.timeBased(), studentGrade.getFirstName(),
					studentGrade.getLastName(), studentGrade.getGrade(), studentGrade.getSubject()));
			return new ResponseEntity<>(sg, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error inserting student grade in database :" + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<StudentGrade> getGradeById(@PathVariable("id") UUID id) {
		Optional<StudentGrade> studentData = dataRepository.findById(id);

		if (studentData.isPresent()) {
			return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
		} else {
			System.out.println("Student with id :" + id + " does not exist in database.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/students")
	public ResponseEntity<List<StudentGrade>> getAllStudentGrades(@RequestParam(required = false) String title) {
		try {
			List<StudentGrade> stu_grades = new ArrayList<StudentGrade>();

			if (title == null)
				dataRepository.findAll().forEach(stu_grades::add);
//	      else
//	    	  dataRepository.findByTitleContaining(title).forEach(stu_grades::add); //TODO wildcard search on first or lastname

			if (stu_grades.isEmpty()) {
				System.out.println("No records exist in database currently.");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("Student records fetched successfully.");
			return new ResponseEntity<>(stu_grades, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Encountered error while fetching student grades :" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/student/{id}")
	public ResponseEntity<StudentGrade> updateStudentGrades(@PathVariable("id") UUID id,
			@RequestBody StudentGrade studentGrade) {
		Optional<StudentGrade> studentData = dataRepository.findById(id);

		if (studentData.isPresent()) {

			StudentGrade sg = studentData.get();
			sg.setGrade(studentGrade.getGrade());
			sg.setSubject(studentGrade.getSubject());

			System.out.println("Student record updated successfully.");
			return new ResponseEntity<>(dataRepository.save(sg), HttpStatus.OK);
		} else {
			System.out.println("Student with id :" + id + " does not exist in database.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/student/{id}")
	public ResponseEntity<HttpStatus> deleteStudentGrade(@PathVariable("id") UUID id) {
		try {
			dataRepository.deleteById(id);
			System.out.println("Student with id :" + id + " deleted successfully.");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(" Could not delete student grade :" + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}

	@DeleteMapping("/studentGrades")
	public ResponseEntity<HttpStatus> deleteAllStudentGrades() {
		try {
			dataRepository.deleteAll();
			System.out.println("Student grades deleted successfully. \n Database is now empty.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println("Error deleting all student grades :" + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}

}
