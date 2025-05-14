package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.JournalEntity;
import com.Utech.journalApp.services.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public  void deleteJournalEntry(@PathVariable String Id) {
        journalEntryService.delete(Id);


    }
    @GetMapping("/find/{id}")
    public JournalEntity findJournalEntry(@PathVariable Long id) {
        return journalEntryService.findById(String.valueOf(id));

    }
}
