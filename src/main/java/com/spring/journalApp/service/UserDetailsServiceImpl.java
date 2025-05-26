package com.spring.journalApp.service;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByUserName(username);
         if(user != null){
             return org.springframework.security.core.userdetails.User.builder()
                     .username(user.getUserName())
                     .password(user.getPassword())
                     .roles(user.getRoles().toArray(new String[0])).build();
         }
         throw new UsernameNotFoundException("User not found with username "+username);
    }
}
