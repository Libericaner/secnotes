package com.security.webapp.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUserDto {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    @JsonCreator
    public JwtUserDto(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
