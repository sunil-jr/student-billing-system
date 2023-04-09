package com.spring.studentproject.service.impl;

import com.spring.studentproject.constant.MessageResponse;
import com.spring.studentproject.dto.student.StudentRequest;
import com.spring.studentproject.dto.student.StudentResponse;
import com.spring.studentproject.model.StudentEntity;
import com.spring.studentproject.repository.StudentRepository;
import com.spring.studentproject.service.StudentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public StudentResponse addStudent(StudentRequest studentRequest) {
        return prepareToStudentResponse(studentRepository.save(prepareToStudentEntity(studentRequest)));
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not valid"));
        return prepareToStudentResponse(studentRepository.save(prepareToUpdateStudentEntity(studentEntity, studentRequest)));
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::prepareToStudentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not valid"));
        return prepareToStudentResponse(studentEntity);
    }

    @Override
    public MessageResponse deleteStudentById(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not valid"));
        studentEntity.setIsDeleted(true);
        studentRepository.save(studentEntity);
        return MessageResponse.builder().message("Student deleted successfully.").build();
    }

    private StudentEntity prepareToStudentEntity(StudentRequest studentRequest) {
        if (studentRequest != null) {
            validateUserNamePresent(studentRequest.getUsername());
            return StudentEntity.builder()
                    .name(studentRequest.getName())
                    .dob(studentRequest.getDob())
                    .username(studentRequest.getUsername())
                    .password(studentRequest.getPassword())
                    .joiningDate(LocalDate.now())
                    .age(validateAgeOfStudent(StudentEntity.builder().dob(studentRequest.getDob()).build()))
                    .isDeleted(false)
                    .build();
        }
        return StudentEntity.builder().build();
    }

    private void validateUserNamePresent(String username) {
        List<StudentEntity> studentByUserNameList = studentRepository.getStudentByUserNameList(username.toUpperCase());
        if (studentByUserNameList.size() > 0) throw new RuntimeException("Student with the username already present");
    }

    private StudentEntity prepareToUpdateStudentEntity(StudentEntity studentEntity, StudentRequest studentRequest) {
        if (studentRequest.getName() != null) {
            if (!studentRequest.getUsername().equalsIgnoreCase(studentEntity.getUsername()))
                validateUserNamePresent(studentEntity.getUsername());
            studentEntity.setName(studentRequest.getName());
        }
        if (studentRequest.getDob() != null) {
            studentEntity.setDob(studentRequest.getDob());
        }
        return studentEntity;
    }

    @Override
    public StudentResponse prepareToStudentResponse(StudentEntity studentEntity) {
        if (studentEntity != null) {
            return StudentResponse.builder()
                    .id(studentEntity.getId())
                    .name(studentEntity.getName())
                    .dob(studentEntity.getDob())
                    .age(validateAgeOfStudent(studentEntity))
                    .joiningDate(studentEntity.getJoiningDate())
                    .username(studentEntity.getUsername())
                    .build();
        }
        return StudentResponse.builder().build();
    }

    private Integer validateAgeOfStudent(StudentEntity studentEntity) {
        LocalDate dob = studentEntity.getDob();
        return Period.between(dob, LocalDate.now()).getYears();
    }

}
