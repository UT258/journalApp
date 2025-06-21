package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.JournalEntity;
import com.Utech.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    //controller class call the service class then service class call the repository class
    @Autowired
    private JournalEntryService journalEntryService;


 @GetMapping
    public List<JournalEntity> getAllJournalEntries() {
        return journalEntryService.getAllJournalEntries();
    }
    @PostMapping
    public void addJournalEntry(@RequestBody JournalEntity journalEntity) {
      journalEntryService.save(journalEntity);
    }
    //there are two ways to send data request paramter and path variable
    @DeleteMapping("id/{Id}")
    public ResponseEntity<JournalEntity> deleteJournalEntry(@PathVariable ObjectId Id) {
        journalEntryService.delete(Id);
        return new ResponseEntity<>( HttpStatus.OK);


    }
    @GetMapping("/find/{id}")
    public JournalEntity findJournalEntry(@PathVariable ObjectId id) {
        return journalEntryService.findById(id);

    }
}
