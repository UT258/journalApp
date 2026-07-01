package com.Utech.journalApp.services;

import com.Utech.journalApp.Entity.JournalEntity;
import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class JournalEntryService {
    private final JournalRepository journalRepository;
    private final UserService userService;
    private final SentimentAnalysisService sentimentAnalysisService;

    public JournalEntryService(JournalRepository journalRepository,
                               UserService userService,
                               SentimentAnalysisService sentimentAnalysisService) {
        this.journalRepository = journalRepository;
        this.userService = userService;
        this.sentimentAnalysisService = sentimentAnalysisService;
    }

    @Transactional
    public JournalEntity save(JournalEntity journalEntity, String username) {
        UserEntity user = userService.findByName(username);
        if (journalEntity.getDate() == null) {
            journalEntity.setDate(new Date());
        }
        enrichSentiment(journalEntity);
        JournalEntity savedEntry = journalRepository.save(journalEntity);
        boolean alreadyLinked = user.getJournalEntries().stream()
                .anyMatch(existingEntry -> savedEntry.getId().equals(existingEntry.getId()));
        if (!alreadyLinked) {
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
        }
        return savedEntry;
    }

    public void delete(ObjectId id) {
        journalRepository.deleteById(id);
    }

    public List<JournalEntity> getAllJournalEntries() {
        return journalRepository.findAll();
    }

    public JournalEntity findById(ObjectId id) {
        return journalRepository.findById(id).orElse(null);
    }

    public boolean userOwnsEntry(UserEntity user, ObjectId entryId) {
        return user != null
                && user.getJournalEntries() != null
                && user.getJournalEntries().stream().anyMatch(entry -> entryId.equals(entry.getId()));
    }

    private void enrichSentiment(JournalEntity journalEntity) {
        String text = String.join(" ",
                journalEntity.getTitle() == null ? "" : journalEntity.getTitle(),
                journalEntity.getContent() == null ? "" : journalEntity.getContent());
        journalEntity.setSentimentScore(sentimentAnalysisService.score(text));
        journalEntity.setSentiment(sentimentAnalysisService.classify(text));
    }
}
