package com.spring.journalApp.repository;

import com.spring.journalApp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class userRepoimptests {
    @Autowired
    userRepoimpl Userrepoimpl;
    @Test
    public void testsaveuser() {
        Userrepoimpl.getUserforSA();
    }
}
