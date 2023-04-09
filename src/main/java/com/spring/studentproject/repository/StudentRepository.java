package com.spring.studentproject.repository;

import com.spring.studentproject.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query("SELECT student FROM StudentEntity student WHERE UPPER(student.username)=:userName")
    Optional<StudentEntity> getStudentByUserName(String userName);

    @Query("SELECT student FROM StudentEntity student WHERE UPPER(student.username)=:userName")
    List<StudentEntity> getStudentByUserNameList(String userName);
}
