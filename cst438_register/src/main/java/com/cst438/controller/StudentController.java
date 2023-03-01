package com.cst438.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
	
//	@PostMapping("/student")
//	@Transactional
//	public String createNewStudent() {
//		return "student id = 12398";
//	}
	

	@PostMapping("/student")
	@Transactional
	public Student createNewStudent( @RequestBody String email, @RequestBody String name) {
		
		//Check for existing student
		//List<Student> studentList = studentRepository.findStudentByEmail(email);
		//Student student = studentList.get(0);
		
		Student student = studentRepository.findByEmail(email);
		
		if (student == null) {
			//Make new student
			student = new Student();
			student.setEmail(email);
			student.setName(name);
			
			Student savedStudent = studentRepository.save(student);
			//ScheduleDTO.CourseDTO result = createCourseDTO(savedStudent);  do I need a StudentDTO (and setter function below?)
			return savedStudent;
		}else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student already exists in database.  "+student.getStudent_id());
		}
		

	}
	

}
