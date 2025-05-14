package com.Utech.journalApp.Repository;

import com.Utech.journalApp.Entity.JournalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<JournalEntity,String> {

}
