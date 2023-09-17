package com.github.patbattb.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class User {

    @Setter
    int id;
    @Email
    @EqualsAndHashCode.Include
    @NotBlank
    private final String email;
    @Pattern(regexp="^\\S+$")
    private final String login;
    private final String nickname;
    @Past
    private final LocalDate birthday;
    private final Set<Integer> friendList;

    public User(String email, String login, String nickname, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (nickname == null || nickname.isBlank()) {
            this.nickname = login;
        } else {
            this.nickname = nickname;
        }
        this.birthday = birthday;
        this.friendList = new HashSet<>();
    }
}
