package com.spring.journalApp.service;

import com.spring.journalApp.entity.User;
import com.spring.journalApp.entity.journalentry;
import com.spring.journalApp.repository.journalentryrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalentryservice   {
    @Autowired
    private journalentryrepo journalentries;
    @Autowired
    private Userservice userservice;
    public void saveEntry(journalentry Journalentry, String userName){
      try{
          User user  = userservice.findByUserName(userName);
          Journalentry.setDate(LocalDateTime.now());
          journalentry saved = journalentries.save(Journalentry);
          user.getJournals().add(saved);
          userservice.saveUser(user);
      }
      catch (Exception e){
          System.out.println(e);
          throw new RuntimeException("an error occured during saving the entry");
      }
     }
     public void saveEntry(journalentry Journalentry){
        journalentries.save(Journalentry);
     }
    public List<journalentry> getAll(){
        return journalentries.findAll();
    }
    public Optional<journalentry> findById(ObjectId id){
        return journalentries.findById(id);
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed =  false;
       try{
           User user = userservice.findByUserName(userName);
           removed = user.getJournals().removeIf(x->x.getId().equals(id));
            if(removed) {
                userservice.saveUser(user);
                journalentries.deleteById(id);
            }
        }
       catch(Exception e){
           System.out.println(e);
       }
       return removed;
    }

}
