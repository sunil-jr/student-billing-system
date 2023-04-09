package com.spring.studentproject.dto.user;

import com.spring.studentproject.constant.UserType;
import lombok.Data;

@Data
public class UserRequest {

    private String username;

    private String password;

    private UserType userType;

}
