package com.spring.journalApp.repository;

import com.spring.journalApp.entity.journalentry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface journalentryrepo extends MongoRepository<journalentry, ObjectId> {

} 