package com.security.webapp.Domain.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity @Setter @Getter
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Titel darf nicht leer sein")
    private String title;

    @NotEmpty(message = "Notiz darf nicht leer sein")
    private String note;

    @NotEmpty(message = "Es muss ein Benutzer vorhanden sein")
    private String username;

}
