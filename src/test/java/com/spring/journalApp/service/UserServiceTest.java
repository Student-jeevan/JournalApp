package com.spring.journalApp.service;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.repository.UserRepo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Userservice userservice;
    @ParameterizedTest
    @ArgumentsSource(UserArgumentprovider.class)
    public void testsaveuser(User user) {
        assertTrue(userservice.savenewEntry(user));
    }
}
