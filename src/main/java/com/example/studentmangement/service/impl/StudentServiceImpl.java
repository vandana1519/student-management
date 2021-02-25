package com.example.studentmangement.service.impl;

import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmangement.entity.Student;
import com.example.studentmangement.repository.StudentRepository;
import com.example.studentmangement.service.StudentService;
import com.example.studentmangement.utils.CsvUtil;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository studentRepository;

	// Store CSV File's data to database
	public void storeStudentRecord(InputStream file) {
		try {
			// Using  Csv Utils to parse CSV file
			List<Student> studentsList = CsvUtil.parseCsvFile(file);
			
			// Save students to database
			studentRepository.saveAll(studentsList);
		} catch(Exception e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}
	
	// Load Data to CSV File
    public void loadFile(Writer writer) {
    	try {
        	List<Student> students = (List<Student>) studentRepository.findAll();
        	
        	// Using CSV Utils to write Students List objects to a Writer
             CsvUtil.studentsToCsv(writer, students);
        	  		
    	} catch(Exception e) {
    		throw new RuntimeException("Fail! -> Message = " + e.getMessage());
    	}
    }
}