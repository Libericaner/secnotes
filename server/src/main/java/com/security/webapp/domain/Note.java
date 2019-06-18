package com.security.webapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Note {

    @Id
    @Size(min = 36, max = 36)
    private String id;

    @NotEmpty(message = "Titel darf nicht leer sein")
    private String title;

    @NotEmpty(message = "Notiz darf nicht leer sein")
    private String note;

    @NotEmpty(message = "Es muss ein Benutzer vorhanden sein")
    private String username;

    @PrePersist
    void enhance() {
        this.id = UUID.randomUUID().toString();
    }

}
