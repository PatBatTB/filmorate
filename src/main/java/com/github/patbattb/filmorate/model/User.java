package com.github.patbattb.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;


@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    private static int currentId = 0;

    int id = ++currentId;
    @Email
    @EqualsAndHashCode.Include
    String email;
    @Pattern(regexp="^\\S+$")
    String login;
    String nickname;
    @Past
    LocalDate birthday;

    public User(String email, String login, String nickname, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (nickname == null || nickname.isBlank()) {
            this.nickname = login;
        } else {
            this.nickname = nickname;
        }
        this.birthday = birthday;
    }
}
