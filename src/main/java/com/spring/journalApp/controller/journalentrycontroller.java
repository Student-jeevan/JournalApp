package com.spring.journalApp.controller;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.entity.journalentry;
import com.spring.journalApp.service.Userservice;
import com.spring.journalApp.service.journalentryservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalentrycontroller {
    @Autowired
    private journalentryservice journalentryser;
    @Autowired
    private Userservice userservice;
    @GetMapping
     public ResponseEntity<?> getAlljournalEntriesOfUser() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userservice.findByUserName(userName);
        List<journalentry> all = user.getJournals();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
     @PostMapping
     public ResponseEntity<journalentry> createEntry(@RequestBody journalentry myEntry){
        try{
            Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalentryser.saveEntry(myEntry , userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
     }
     @GetMapping("id/{myId}")
     public ResponseEntity<journalentry> getJournalentryByid(@PathVariable ObjectId myId){
         Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
         String userName = authentication.getName();
         User user = userservice.findByUserName(userName);
         List<journalentry> collect = user.getJournals().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<journalentry> journalEntry= journalentryser.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get() , HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
     @DeleteMapping("id/{myid}")
     public ResponseEntity<?> deletejournalentrybyId(@PathVariable ObjectId myid){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalentryser.deleteById(myid , userName);
        if(removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
     }
     @PutMapping("id/{id}")
     public ResponseEntity<?> upadatejournalbyId(@PathVariable ObjectId id , @RequestBody journalentry newEntry){
         Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
         String userName = authentication.getName();
         User user = userservice.findByUserName(userName);
         List<journalentry> collect = user.getJournals().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
         if(!collect.isEmpty()){
             Optional<journalentry> journalEntry= journalentryser.findById(id);
             if(journalEntry.isPresent()) {
                 journalentry old = journalEntry.get();
                 old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                 old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                 journalentryser.saveEntry(old);
                 return new ResponseEntity<>(old, HttpStatus.OK);
             }
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
}
