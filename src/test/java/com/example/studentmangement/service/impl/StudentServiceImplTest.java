package com.example.studentmangement.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.studentmangement.entity.Student;
import com.example.studentmangement.repository.StudentRepository;

@SpringBootTest
class StudentServiceImplTest {
	
	@Spy
	@InjectMocks
	StudentServiceImpl studentServiceImpl;
	
	@Mock
	StudentRepository studentRepository;
	
	static Student student1 = new Student();
	static Student student2 = new Student();
	static List<Student> studentList = new ArrayList<>();
	static HttpServletResponse response;
	
	@BeforeAll
	public static void setup() {
		student1.setId(100L);
		student1.setName("Vandana");
		student1.setAge(26);

		student2.setId(101L);
		student2.setName("Sanjana");
		student2.setAge(27);
		studentList.add(student1);
		studentList.add(student2);
		
		response  = mock(HttpServletResponse.class);
	}
	
	@Test
	void testStoreStudentRecordSuccess() throws Exception{
		InputStream file = new ByteArrayInputStream(student1.toString().getBytes(StandardCharsets.UTF_8));
		Mockito.when(studentRepository.saveAll(studentList)).thenReturn(studentList);
		Mockito.verify(studentServiceImpl,times(1)).storeStudentRecord(file);
	}

}
