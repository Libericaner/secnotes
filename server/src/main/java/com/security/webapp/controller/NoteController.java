package com.security.webapp.controller;

import com.security.webapp.domain.Note;
import com.security.webapp.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;

        noteService.add(new Note("Todos", "Brot holen"), "joel");
        noteService.add(new Note("Schule", "Test lernen"), "joel");
        noteService.add(new Note("Sport", "Fallschirm nähen"), "david");
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getNotes() {
        return ResponseEntity.ok(noteService.getAllNotes(getUsername()));
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNote(@PathVariable String id){
        return ResponseEntity.ok(noteService.getNote(id, getUsername()));
    }

    @PostMapping("/note")
    public ResponseEntity createNote(@RequestBody Note note){
        return ResponseEntity.ok(noteService.add(note, getUsername()));
    }

    @PutMapping("/note")
    public void editNote(@RequestBody Note note) {
        noteService.update(note, getUsername());
    }

    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable String id){
        noteService.delete(id, getUsername());
    }

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }
}
