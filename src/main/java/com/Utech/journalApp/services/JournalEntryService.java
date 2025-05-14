package com.Utech.journalApp.services;

import com.Utech.journalApp.Entity.JournalEntity;
import com.Utech.journalApp.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component //this is used to mark this class as a Spring component
public class JournalEntryService {
  //save the data
    @Autowired  //this is used to inject the JournalRepository bean into this class
    private JournalRepository journalRepository;
    public void save(JournalEntity myJournalEntity) {
        journalRepository.save(myJournalEntity);
    }
    public void  delete(ObjectId id ) {
        journalRepository.deleteById(id);
    }
    public List<JournalEntity>getAllJournalEntries() {
        return journalRepository.findAll();
    }
    public JournalEntity findById(ObjectId id) {
        return journalRepository.findById(id).orElse(null);
    }
    //this will save the data in the database
}
