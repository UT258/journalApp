package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.JournalEntity;
import com.Utech.journalApp.Entity.Sentiment;
import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.services.JournalEntryService;
import com.Utech.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    private final JournalEntryService journalEntryService;
    private final UserService userService;

    public JournalEntryControllerV2(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    private UserEntity getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return (username != null && !username.isBlank()) ? userService.findByName(username) : null;
    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntries(@RequestParam(required = false) String tag) {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated or not found");
        }

        List<JournalEntity> entries = user.getJournalEntries() == null ? List.of() : user.getJournalEntries();
        if (tag != null && !tag.isBlank()) {
            entries = entries.stream()
                    .filter(entry -> entry.getTags() != null && entry.getTags().contains(tag))
                    .toList();
        }
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<?> addJournalEntry(@RequestBody JournalEntity journalEntity) {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated or not found");
        }
        if (journalEntity == null || journalEntity.getTitle() == null || journalEntity.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Journal entry title is required");
        }

        JournalEntity savedEntry = journalEntryService.save(journalEntity, user.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId id) {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        if (!journalEntryService.userOwnsEntry(user, id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found for deletion");
        }

        user.getJournalEntries().removeIf(entry -> id.equals(entry.getId()));
        userService.saveUser(user);
        journalEntryService.delete(id);
        return ResponseEntity.ok("Journal entry deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntity journalEntity) {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        if (!journalEntryService.userOwnsEntry(user, id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found");
        }

        JournalEntity currentEntry = journalEntryService.findById(id);
        currentEntry.setTitle(journalEntity.getTitle());
        currentEntry.setContent(journalEntity.getContent());
        currentEntry.setDate(journalEntity.getDate());
        currentEntry.setTags(journalEntity.getTags());
        return ResponseEntity.ok(journalEntryService.save(currentEntry, user.getUserName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findJournalEntry(@PathVariable ObjectId id) {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        if (!journalEntryService.userOwnsEntry(user, id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found");
        }
        return ResponseEntity.ok(journalEntryService.findById(id));
    }

    @GetMapping("/trends")
    public ResponseEntity<Map<Sentiment, Long>> getSentimentTrends() {
        UserEntity user = getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<JournalEntity> entries = user.getJournalEntries() == null ? List.of() : user.getJournalEntries();
        Map<Sentiment, Long> trends = entries.stream()
                .collect(Collectors.groupingBy(JournalEntity::getSentiment, LinkedHashMap::new, Collectors.counting()));
        return ResponseEntity.ok(trends);
    }
}
