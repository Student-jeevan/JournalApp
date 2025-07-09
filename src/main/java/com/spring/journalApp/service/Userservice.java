package com.spring.journalApp.service;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class Userservice {
    @Autowired
    private UserRepo userrep;
    private static final PasswordEncoder passwordencoder  = new BCryptPasswordEncoder();

    public boolean savenewEntry(User user){
       try{
           user.setPassword(passwordencoder.encode(user.getPassword()));
           user.setRoles(Arrays.asList("USER"));
           userrep.save(user);
           return true;
       }
       catch(Exception e){
           log.error("error occured for user name {} ");
           log.warn("warining occured");
           log.info("info occured");
           log.debug("debug occured");
           return false;
       }

    }
    public void saveAdmin(User user){
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userrep.save(user);

    }
    public void saveUser(User user){
        userrep.save(user);
    }
    public List<User> getAll(){
        return userrep.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return userrep.findById(id);
    }
    public void deleteById(ObjectId id){

        userrep.deleteById(id);
    }
    public User findByUserName(String userName){

        return userrep.findByUserName(userName);
    }
}
