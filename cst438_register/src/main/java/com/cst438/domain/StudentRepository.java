package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository <Student, Integer> {
	
	// declare the following method to return a single Student object
	// default JPA behavior that findBy methods return List<Student> except for findById.
	
	public Student findByEmail(String email);
	public Student findById(int id);
	
	public void deleteByEmail(String email);
	public void deleteById(int id);
	
	public boolean existsByEmail(String email);
	
	
//	@Query("select e from Student e where e.student.email=:email")
//	List<Student> findByEmail(String email);
//
//	@Query("select 1 from Student e where e.student.email=:email and e.name=:name")
//	public List<Student> findStudentByEmail(@Param("email") String email);
//	
//	@Query("select 1 from Student e where e.student.email=:email")
//	public Student findStudentByEmail(@Param("email") String email);
//	
//	@Query("select e from Student e where e.student.email=:email")
//	public Student findByEmail(@Param("email") String email);
//
//	@SuppressWarnings("unchecked")
//	Student save(Student e);
//
}
	
