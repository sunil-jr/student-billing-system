package com.spring.studentproject.service;

import com.spring.studentproject.constant.MessageResponse;
import com.spring.studentproject.constant.UserType;
import com.spring.studentproject.dto.student.StudentRequest;
import com.spring.studentproject.dto.student.StudentResponse;
import com.spring.studentproject.model.StudentEntity;

import java.util.List;

public interface StudentService {

    StudentResponse addStudent(StudentRequest studentRequest);

    StudentResponse updateStudent(Long id, StudentRequest studentRequest);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    MessageResponse deleteStudentById(Long id);

    StudentResponse prepareToStudentResponse(StudentEntity studentEntity);
}
