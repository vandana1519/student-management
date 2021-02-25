package com.example.studentmangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.studentmangement.entity.Student;

@Repository
public interface StudentRepository  extends CrudRepository<Student, Long>{

}
