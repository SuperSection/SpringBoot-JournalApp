package com.supersection.journalApp.controller;

import org.bson.types.ObjectId;
import com.supersection.journalApp.enitity.JournalEntry;
import com.supersection.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        List<JournalEntry> allJournalEntriesByUser = journalEntryService.getAll(username);
        if (allJournalEntriesByUser != null && !allJournalEntriesByUser.isEmpty()) {
            return new ResponseEntity<>(allJournalEntriesByUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username) {
        try {
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{journalId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId journalId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(journalId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{username}/id/{journalId}")
    public ResponseEntity<?> updateJournalById(@PathVariable String username, @PathVariable ObjectId journalId) {
        journalEntryService.deleteById(journalId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{username}/id/{journalId}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(
            @PathVariable String username,
            @PathVariable ObjectId journalId,
            @RequestBody JournalEntry updatedEntry
    ) {
        JournalEntry journalEntry = journalEntryService.findById(journalId).orElse(null);
        if (journalEntry != null) {
            journalEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty() ? updatedEntry.getTitle() : journalEntry.getTitle());
            journalEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty() ? updatedEntry.getContent() : journalEntry.getContent());
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
