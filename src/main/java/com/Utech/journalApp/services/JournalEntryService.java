package com.Utech.journalApp.services;

import com.Utech.journalApp.Entity.JournalEntity;
import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Component //this is used to mark this class as a Spring component
public class JournalEntryService {
  //save the data
    @Autowired  //this is used to inject the JournalRepository bean into this class
    private JournalRepository journalRepository;
    @Autowired
    private  UserService userService; // Assuming you have a UserService to handle user-related operations
    @Transactional
    public void save(JournalEntity myJournalEntity,String username) {
        //you can add the username to the JournalEntity if you want to associate it with a
        UserEntity user = userService.findByName(username);//got the user
        JournalEntity journalEntity = journalRepository.save(myJournalEntity);
        //now add this to jounral entry of user
        user.getJournalEntries().add(journalEntity);

        userService.saveUser(user); // Save the updated user with the new journal entry
        //this will save the user with the new journal entry
        //so we have to save the user entity with the new journal entry
        //this will save the journal entry in the database
        //and also add the journal entry to the user entity


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
