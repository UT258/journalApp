package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.JournalEntity;
import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.services.JournalEntryService;
import com.Utech.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
 // Optional: Allow requests from frontend
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private UserEntity getAuthenticatedUser() {
        String username = getAuthenticatedUsername();
        return (username != null && !username.isEmpty()) ? userService.findByName(username) : null;
    }

    // GET all journal entries for the authenticated user
    @GetMapping
    public ResponseEntity<?> getAllJournalEntries() {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated or not found");
        }

        List<JournalEntity> entries = user.getJournalEntries();
        if (entries == null || entries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No journal entries found");
        }

        return ResponseEntity.ok(entries);
    }

    // POST a new journal entry
    @PostMapping
    public ResponseEntity<?> addJournalEntry(@RequestBody JournalEntity journalEntity) {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated or not found");
        }

        if (journalEntity == null || journalEntity.getTitle() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid journal entry data");
        }

        journalEntryService.save(journalEntity, user.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry added successfully");
    }

    // DELETE a journal entry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId id) {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        List<JournalEntity> entries = user.getJournalEntries();
        if (entries == null || entries.stream().noneMatch(e -> e.getId().equals(id))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found for deletion");
        }

        entries.removeIf(entry -> entry.getId().equals(id));
        userService.saveUser(user);
        journalEntryService.delete(id);

        return ResponseEntity.ok("Journal entry deleted successfully");
    }

    // PUT to update an existing journal entry
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntity journalEntity) {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        JournalEntity currentEntry = journalEntryService.findById(id);
        if (currentEntry == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found");
        }

        currentEntry.setTitle(journalEntity.getTitle());
        currentEntry.setContent(journalEntity.getContent());
        currentEntry.setDate(journalEntity.getDate());

        journalEntryService.save(currentEntry, user.getUserName());

        return ResponseEntity.ok("Journal entry updated successfully");
    }

    // GET a journal entry by ID
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findJournalEntry(@PathVariable ObjectId id) {
        JournalEntity entry = journalEntryService.findById(id);
        if (entry == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found");
        }
        return ResponseEntity.ok(entry);
    }
}
