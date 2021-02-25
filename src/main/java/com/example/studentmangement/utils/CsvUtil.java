package com.example.studentmangement.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.example.studentmangement.entity.Student;

public class CsvUtil {

	private static String csvExtension = "csv";

	public static void studentsToCsv(Writer writer, List<Student> students) throws IOException {

		try (CSVPrinter csvPrinter = new CSVPrinter(writer,
				CSVFormat.DEFAULT.withHeader("id", "name", "age"));) {
			for (Student student : students) {
				List<String> data = Arrays.asList(String.valueOf(student.getId()), student.getName(),
						String.valueOf(student.getAge()));

				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Student> parseCsvFile(InputStream is) {

		List<Student> students = new ArrayList<>();

		try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
			
			
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Student student = new Student(Long.parseLong(csvRecord.get("id")), csvRecord.get("name"),
						Integer.parseInt(csvRecord.get("age")));

				students.add(student);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return students;
	}

	public static boolean isCSVFile(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];

		return extension.equals(csvExtension);

	}
}
