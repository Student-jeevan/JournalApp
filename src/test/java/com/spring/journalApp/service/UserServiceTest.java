package com.spring.journalApp.service;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepo userRepo;
    @ParameterizedTest
    @ArgumentsSources(UserArgumentprovider.class)
    public void testfindByUsername(User name){
        User user = userRepo.findByUserName(name);
        assertTrue(!user.getJournals().isEmpty());
    }
}
