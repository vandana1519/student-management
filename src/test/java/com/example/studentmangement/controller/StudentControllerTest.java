package com.example.studentmangement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.studentmangement.entity.Student;
import com.example.studentmangement.service.StudentService;


@WebMvcTest(value = StudentController.class)
public class StudentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	StudentService studentService;
	
	static Student student = new Student();
	static HttpServletResponse response;
	
	@BeforeAll
	public static void setup() {
		student.setId(100L);
		student.setName("Vandana");
		student.setAge(26);
		
		response  = mock(HttpServletResponse.class);
	}
	
	@Test
	void testSaveStudentRecordSuccess() throws Exception{
		
	    MockMultipartFile mockFile = new MockMultipartFile("mockFile", "test.csv", "text/csv", student.toString().getBytes("utf-8"));
	    MockMvc mockMvc 
	      = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	    
		 MvcResult result = mockMvc.perform(multipart("/api/student/uploadRecord").file(mockFile)).andReturn();
		 
		 assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	
	}
	
	@Test
    void testDownloadFileSuccess() throws Exception {		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=student.csv");
        Mockito.doNothing().when(studentService).loadFile(response.getWriter());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/downloadRecord").contentType(MediaType.APPLICATION_OCTET_STREAM)).andReturn();
        Assertions.assertEquals("text/csv", result.getResponse().getContentType());
    }

}
