package com.spring.studentproject.dto.student;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StudentResponse {

    private Long id;

    private String name;

    private String username;

    private Integer age;

    private LocalDate dob;

    private LocalDate joiningDate;

}
