package com.security.webapp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

    List<Note> findAllByUsername(String username);
    Optional<Note> findByUsernameAndId(String username, String id);
}
