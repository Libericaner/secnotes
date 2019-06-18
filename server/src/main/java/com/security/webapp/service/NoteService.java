package com.security.webapp.service;

import com.security.webapp.domain.Note;
import com.security.webapp.domain.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {

        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes(String username) {

        return noteRepository.findAllByUsername(username);
    }

    public Note getNote(String id, String username) {

        return noteRepository.findByUsernameAndId(username, id).orElse(null);
    }

    public Note add(Note note, String username) {

        note.setUsername(username);
        return noteRepository.save(note);
    }

    public void update(Note note, String username) {

        noteRepository.findByUsernameAndId(username, note.getId()).ifPresentOrElse(old -> {
            old.setNote(note.getNote());
            old.setTitle(note.getTitle());
            noteRepository.save(old);
        }, IllegalArgumentException::new);
    }

    public void delete(String id, String username){

        Optional<Note> note = noteRepository.findById(id);
        note.ifPresent(n -> {
            if (n.getUsername().equals(username)) {
                noteRepository.deleteById(n.getId());
            }
        });
    }

}
