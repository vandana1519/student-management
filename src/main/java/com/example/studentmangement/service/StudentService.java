package com.example.studentmangement.service;

import java.io.InputStream;
import java.io.Writer;

public interface StudentService {
	
	void storeStudentRecord(InputStream file);
	
	void loadFile(Writer writer);

}
