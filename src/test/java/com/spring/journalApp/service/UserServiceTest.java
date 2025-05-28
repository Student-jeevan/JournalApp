package com.spring.journalApp.service;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepo userRepo;
    @Test
    public void testfindByUsername(){

        User user = userRepo.findByUserName("Ramsingh");
        assertTrue(!user.getJournals().isEmpty());
    }
}
