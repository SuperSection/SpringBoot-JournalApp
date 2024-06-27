package com.supersection.journalApp.service;

import com.supersection.journalApp.enitity.JournalEntry;
import com.supersection.journalApp.enitity.UserEntity;
import com.supersection.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            UserEntity user = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            // save new journal entry to the DB
            JournalEntry savedJournal = journalEntryRepository.save(journalEntry);
            // add the journal entry to the user document and save the user
            user.getJournalEntries().add(savedJournal);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("Exception while saving journal entry - ", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        try {
            // save new journal entry to the DB
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            log.error("Exception while saving journal entry - ", e);
        }
    }

    public List<JournalEntry> getAll(String username) {
        UserEntity user = userService.findByUsername(username);
        if (user != null) {
            return user.getJournalEntries();
        }
        log.error("Failed to find user with the Username");
        return null;
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId journalId, String username) {
        UserEntity user = userService.findByUsername(username);
        user.getJournalEntries().removeIf(journal -> journal.getId().equals(journalId));
        userService.saveUser(user);
        journalEntryRepository.deleteById(journalId);
    }

}