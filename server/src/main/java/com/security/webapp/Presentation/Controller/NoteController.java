package com.security.webapp.Presentation.Controller;

import com.security.webapp.Domain.Entity.Note;
import com.security.webapp.Domain.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNote(@PathVariable long id){
        return ResponseEntity.ok(noteService.getNote(id));
    }

    @PostMapping("/note")
    public void setNote(@RequestBody Note note){
        noteService.add(note);
    }

    @PutMapping("/note/{id}")
    public void editNote(@PathVariable long id, @RequestBody Note note){
        noteService.update(id, note);
    }

    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable long id){
        noteService.delete(id);
    }





    // Method to serve the guest page!
    @GetMapping(value= "/guest")
    public ResponseEntity<String> guest() {
        System.out.println("Showing guest page.");
        return new ResponseEntity<String>("Hello from guest page!", HttpStatus.OK);
    }

    // Method to serve the secure/administration page!
    @GetMapping(value= "/admin")
    public ResponseEntity<String> admin() {
        System.out.println("Showing administrator page.");
        return new ResponseEntity<String>("Welcome to secure/admin page!", HttpStatus.OK);
    }

}
