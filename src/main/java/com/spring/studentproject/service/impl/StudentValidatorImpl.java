package com.spring.studentproject.service.impl;

import com.spring.studentproject.model.StudentEntity;
import com.spring.studentproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentValidatorImpl implements StudentValidator {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentValidatorImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEntity validateByUserName(String userName) {
        Optional<StudentEntity> studentByUserName = studentRepository.getStudentByUserName(userName.toUpperCase());
        if (studentByUserName.isEmpty()) throw new RuntimeException("Not Valid User");
        return studentByUserName.get();
    }
}
