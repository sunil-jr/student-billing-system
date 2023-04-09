package com.spring.studentproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Age")
    private Integer age;

    @Column(name = "Date of Birth")
    private LocalDate dob;

    @Column(name = "Joining Date")
    private LocalDate joiningDate;

    @Column(name="Is Deleted")
    private Boolean isDeleted = false;

    @Column(unique = true)
    private String username;

    private String password;
}
