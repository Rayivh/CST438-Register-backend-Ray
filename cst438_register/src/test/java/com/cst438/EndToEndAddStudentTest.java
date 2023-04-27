package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
public class EndToEndAddStudentTest {
	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";
	public static final String URL = "http://localhost:3000";
	public static final String TEST_USER_EMAIL = "test@csumb.edu";
	public static final String TEST_USER_NAME = "test";
	public static final int TEST_COURSE_ID = 40443; 
	public static final int TEST_ID = 1; 
	public static final String TEST_SEMESTER = "2021 Fall";
	public static final int SLEEP_DURATION = 1000; // 1 second.

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Test
	public void addStudentTest() throws Exception {
		
		// set the driver location and start driver
				//@formatter:off
				// browser	property name 				Java Driver Class
				// edge 	webdriver.edge.driver 		EdgeDriver
				// FireFox 	webdriver.firefox.driver 	FirefoxDriver
				// IE 		webdriver.ie.driver 		InternetExplorerDriver
				//@formatter:on
				
				/*
				 * initialize the WebDriver and get the home page. 
				 */
				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
				WebDriver driver = new ChromeDriver();
				// Puts an Implicit wait for 10 seconds before throwing exception
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get(URL);
				Thread.sleep(SLEEP_DURATION);
				
				
				
				Student x = null;
				do {
					x = studentRepository.findByEmail(TEST_USER_EMAIL);
					if (x != null)
						studentRepository.delete(x);
				} while (x != null);
		   
		        try {
					
		        	driver.get(URL);
					Thread.sleep(SLEEP_DURATION);
					// select the Button Group Button
					
					WebElement we = driver.findElement(By.xpath("(//input[@type='ButtonGroup'])[last()]"));
					we.click();
					// Locate and click "Add Student" button
					
					driver.findElement(By.xpath("//a")).click();
					Thread.sleep(SLEEP_DURATION);
					// enter Student name and click Add button
					
					driver.findElement(By.xpath("//input[@name='Name']")).sendKeys(TEST_USER_NAME);
					driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(TEST_USER_EMAIL);
					driver.findElement(By.xpath("//button[@id='Add']")).click();
					Thread.sleep(SLEEP_DURATION);

					/*
					* verify that new student shows in enrollment.
					* get the names of all students listed in schedule
					*/ 

					Student student = studentRepository.findById(TEST_ID).get();
					
					List<WebElement> elements  = driver.findElements(By.xpath("//div[@data-field='title']/div[@class='MuiDataGrid-cellContent']"));
					boolean found = false;
					for (WebElement e : elements) {
						System.out.println(e.getText()); // for debug
						if (e.getText().equals(student.getName())) {
							found=true;
							break;
						}
					}
					assertTrue( found, "Student added but not listed in schedule.");
					
					// verify that enrollment row has been inserted to database.
					
					Enrollment e = enrollmentRepository.findByEmailAndCourseId(TEST_USER_EMAIL, TEST_COURSE_ID);
					assertNotNull(e, "Student not found in database.");
				}catch (Exception ex) {
					throw ex;
				} finally {
					/*
					 *  clean up database so the test is repeatable.
					 */
					Enrollment e = enrollmentRepository.findByEmailAndCourseId(TEST_USER_EMAIL, TEST_COURSE_ID);
					if (e!=null) enrollmentRepository.delete(e);
		
					driver.quit();
				}
	}
}