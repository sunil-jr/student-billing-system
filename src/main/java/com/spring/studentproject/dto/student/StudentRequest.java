package com.spring.studentproject.dto.student;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequest {

    private String name;

    private LocalDate dob;

    private String username;

    private String password;

}
