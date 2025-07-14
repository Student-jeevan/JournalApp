package com.spring.journalApp.controller;
import com.spring.journalApp.api.Response.WeatherResponse;
import com.spring.journalApp.entity.User;
import com.spring.journalApp.repository.UserRepo;
import com.spring.journalApp.service.Userservice;
import com.spring.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/user")
public class Usercontroller {
    @Autowired
    private Userservice userservice;
    @Autowired
    private UserRepo userrep;
    @Autowired
    private WeatherService weatherService;
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
         User userindb = userservice.findByUserName(userName);
         userindb.setUserName(user.getUserName());
         userindb.setPassword(user.getPassword());
         userservice.savenewEntry(userindb);

     return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @DeleteMapping
    public ResponseEntity<?> deleteuserbyid(@RequestBody User user){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        userrep.deleteByuserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getweather("Mumbai");
        String greeting = "";
        if(weatherResponse != null && weatherResponse.getCurrent() != null){
            greeting = "weathter feels like "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+authentication.getName()+ greeting , HttpStatus.OK);
    }
}
