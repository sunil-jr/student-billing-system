package com.spring.studentproject.dto.user;

import com.spring.studentproject.constant.UserType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {

    private Long id;

    private UserType userType;

    private String username;
}
