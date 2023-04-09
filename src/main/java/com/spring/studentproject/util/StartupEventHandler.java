package com.spring.studentproject.util;

import com.spring.studentproject.constant.UserType;
import com.spring.studentproject.model.UserEntity;
import com.spring.studentproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupEventHandler implements ApplicationListener<ApplicationStartedEvent> {

    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        loadUser();
    }

    private void loadUser() {
        createUser("admin", "admin", UserType.ADMIN);
    }

    private void createUser(String username, String password, UserType userType) {
        if(!userRepository.existsByUsername(username)){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setUserType(userType);
            userRepository.save(userEntity);
        }
    }

}
