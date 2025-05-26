package com.spring.journalApp.service;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class Userservice {
    @Autowired
    private UserRepo userrep;
    private static final PasswordEncoder passwordencoder  = new BCryptPasswordEncoder();
    public void savenewEntry(User user){
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userrep.save(user);

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
