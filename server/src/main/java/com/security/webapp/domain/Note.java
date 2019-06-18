package com.security.webapp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Note(@NotEmpty(message = "Titel darf nicht leer sein") String title,
                @NotEmpty(message = "Notiz darf nicht leer sein") String note) {
        this.title = title;
        this.note = note;
    }

    @PrePersist
    void enhance() {
        this.id = UUID.randomUUID().toString();
    }
}
