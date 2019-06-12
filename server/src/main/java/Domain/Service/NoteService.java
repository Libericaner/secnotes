package Domain.Service;

import Data.Repository.NoteRepository;
import Domain.Entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public String auth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }

    public List<Note> getAllNotes(){
        return noteRepository.findAllByUsername(auth());
    }

    public Note getNote(long id){
        return noteRepository.findByUsernameAndId(auth(), id);
    }

    public void add(Note note){
        note.setUsername(auth());
        noteRepository.save(note);
    }

    public void update(long id, Note note){
        Note old = noteRepository.findByUsernameAndId(auth(), id);
        old.setNote(note.getNote());
        old.setTitle(note.getNote());
        noteRepository.save(note);
    }

    public void delete(long id){
        Note note = noteRepository.findNoteById(id);

        if(note.getUsername() == auth())
            noteRepository.deleteById(id);
    }

}
