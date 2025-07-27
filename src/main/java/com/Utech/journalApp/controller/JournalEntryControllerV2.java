package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.JournalEntity;
import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.services.JournalEntryService;
import com.Utech.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    // This controller handles all the API calls related to journals for a specific user.

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // ✅ GET all journal entries for a user
    @GetMapping("{UserName}")
    public ResponseEntity<?> getAllJournalEntries(@PathVariable String UserName) {
        // Find the user by name
        UserEntity user = userService.findByName(UserName);

        // Get journal entries from the user object
        List<JournalEntity> all = user.getJournalEntries();

        // If the user has no entries
        if (all == null || all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No journal entries found for user: " + UserName);
        }

        return ResponseEntity.ok(all); // Return all entries
    }

    // ✅ POST a new journal entry for a user
    @PostMapping("{UserName}")
    public void addJournalEntry(@RequestBody JournalEntity journalEntity, @PathVariable String UserName) {
        // Add journal entry to the user and database
        journalEntryService.save(journalEntity, UserName);
    }

    // ✅ DELETE a journal entry by ID and UserName
    @DeleteMapping("id/{Id}/{UserName}")
    public ResponseEntity<JournalEntity> deleteJournalEntry(@PathVariable ObjectId Id, @PathVariable String UserName) {
        // Find the user
        UserEntity user = userService.findByName(UserName);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Remove journal entry from the user's list
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(Id));
        userService.saveUser(user); // Save updated user

        // Delete journal entry from DB
        journalEntryService.delete(Id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ✅ PUT to update an existing journal entry
    @PutMapping("/update/{id}/{UserName}")
    public ResponseEntity<?> updateJournalEntry(@RequestBody JournalEntity journalEntity, @PathVariable String UserName) {
        // Find the user
        UserEntity user = userService.findByName(UserName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }

        // Find the existing journal entry by ID
        JournalEntity currentEntry = journalEntryService.findById(journalEntity.getId());
        if (currentEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Journal not found
        }

        // Update fields
        currentEntry.setTitle(journalEntity.getTitle());
        currentEntry.setContent(journalEntity.getContent());
        currentEntry.setDate(journalEntity.getDate());

        // Save the updated journal entry
        journalEntryService.save(currentEntry, UserName);

        return ResponseEntity.ok("Journal entry updated successfully");
    }

    // ✅ GET a single journal entry by its ID
    @GetMapping("/find/{id}")
    public JournalEntity findJournalEntry(@PathVariable ObjectId id) {
        return journalEntryService.findById(id);
    }
}
