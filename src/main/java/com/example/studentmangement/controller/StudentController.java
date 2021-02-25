package com.example.studentmangement.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.studentmangement.dto.Message;
import com.example.studentmangement.dto.ResponseDto;
import com.example.studentmangement.service.StudentService;
import com.example.studentmangement.utils.CsvUtil;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping("/uploadRecord")
	public ResponseDto saveStudentRecord(@RequestParam("csvfile") MultipartFile csvfile) {
		ResponseDto response = new ResponseDto();

		// Checking the upload-file's name before processing
		if (csvfile.getOriginalFilename().isEmpty()) {
			response.addMessage(new Message(csvfile.getOriginalFilename(), "No selected file to upload!", "FAIL"));

			return response;
		}

		// checking the upload file's type is CSV or NOT
		if (!CsvUtil.isCSVFile(csvfile)) {
			response.addMessage(new Message(csvfile.getOriginalFilename(), "Error: Not a CSV file!", "FAIL"));
			return response;
		}

		try {
			// save file data to database
			studentService.storeStudentRecord(csvfile.getInputStream());
			response.addMessage(new Message(csvfile.getOriginalFilename(), "File Uploaded Successfully!", "OK"));
		} catch (Exception e) {
			response.addMessage(new Message(csvfile.getOriginalFilename(), e.getMessage(), "FAIL"));
		}

		return response;
	}

	@GetMapping("/downloadRecord")
	public void downloadFile(HttpServletResponse response) throws IOException {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=student.csv");
		studentService.loadFile(response.getWriter());
	}

}
