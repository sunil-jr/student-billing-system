package com.spring.studentproject.controller;

import com.spring.studentproject.constant.MessageResponse;
import com.spring.studentproject.dto.student.StudentRequest;
import com.spring.studentproject.dto.student.StudentResponse;
import com.spring.studentproject.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/mis")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/addStudent")
    public StudentResponse addStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.addStudent(studentRequest);
    }

    @PutMapping("/updateStudent/{id}")
    public StudentResponse updateStudent(@PathVariable(value="id") Long id, @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id,studentRequest);
    }

    @GetMapping("/getAllStudents")
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable(value="id") Long id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public MessageResponse deleteStudentById(@PathVariable(value="id") Long id) {
        return studentService.deleteStudentById(id);
    }
}
