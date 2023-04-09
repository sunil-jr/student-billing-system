package com.spring.studentproject.service.impl;

import com.spring.studentproject.model.StudentEntity;

public interface StudentValidator {

    StudentEntity validateByUserName(String userName);
}
