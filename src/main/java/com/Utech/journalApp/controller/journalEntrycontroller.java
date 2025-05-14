package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.JournalEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
//@RequestMapping("/journal")
public class journalEntrycontroller {
    private Map<String ,JournalEntity>JournalEntry = new HashMap<>();
    //this is the map which will store the journal entries
    //rest api will be used to add the journal entries
    //and get the journal entries
    @GetMapping
    public List<JournalEntity> getAllJournalEntries() {
        return JournalEntry.values().stream().toList();
    }
    @PostMapping
    public void addJournalEntry(@RequestBody JournalEntity journalEntity) {
        JournalEntry.put(journalEntity.getId(), journalEntity);
    }
    //there are two ways to send data request paramter and path variable
    @DeleteMapping("id/{Id}")
    public  void deleteJournalEntry(@PathVariable Long Id) {
        JournalEntry.remove(Id);

    }
    @GetMapping("/find/{id}")
    public JournalEntity findJournalEntry(@PathVariable Long id) {
        return JournalEntry.get(id);

    }


}
