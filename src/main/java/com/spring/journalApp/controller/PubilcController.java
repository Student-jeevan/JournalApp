package com.spring.journalApp.controller;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PubilcController {
    @Autowired
    private Userservice userservice;
    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userservice.savenewEntry(user);
    }
}
