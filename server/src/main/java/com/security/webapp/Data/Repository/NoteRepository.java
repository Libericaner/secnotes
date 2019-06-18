package com.security.webapp.Data.Repository;

import com.security.webapp.Domain.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUsername(String username);
    Note findByUsernameAndId(String username, long id);
    Note findNoteById(long id);
}
