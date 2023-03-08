package com.cst438.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
public class StudentController {

	
	
	@Autowired
	StudentRepository studentRepository;
	
	
	@GetMapping("/student")
	public Student getStudentbyEmail( @RequestBody String email) {
		Student student = studentRepository.findByEmail(email);
		if (student != null) {
			System.out.println("/student student "+student.getName()+" "+student.getStudent_id());
			return student;
		}else {
			throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student not found.  ");
		}
		
	}

// THIS WORKS!
	@PostMapping("/student")
	@Transactional
	public Student createNewStudent( @RequestBody Student student) {
		if (!studentRepository.existsByEmail(student.getEmail())) {
			return studentRepository.save(student);
		}else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student already exists in database.");
		}
	}
	

//THIS WORKS!
	@DeleteMapping("/student")
	@Transactional
	public void deleteStudent( @RequestBody Student student) {
		String email = student.getEmail();
		if (studentRepository.existsByEmail(email)) {
			//Delete Student using appropriate SQL query
			studentRepository.deleteByEmail(email);
		}else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student does not exist in database.");
		}
		
	}
	
// //THIS WORKS!
//		@DeleteMapping("/student")
//		@Transactional
//		public void deleteStudent( @RequestBody Student student) {
//			String email = student.getEmail();
//			Student databaseStudent = studentRepository.findByEmail(email);
//			//Student student = studentRepository.findById(id);
//			if (databaseStudent != null) {
//				//Delete Student using appropriate SQL query
//				studentRepository.deleteByEmail(email);
//			}else {
//				throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student with email: " + email + " does not exist in database.");
//			}
//			
//		}
}
