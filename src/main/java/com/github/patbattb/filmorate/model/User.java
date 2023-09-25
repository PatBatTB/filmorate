package com.github.patbattb.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class User {

    @Setter
    private int id;
    @Email
    @EqualsAndHashCode.Include
    @NotBlank
    private final String email;
    @Pattern(regexp = "^\\S+$")
    private final String login;
    private final String nickname;
    @Past
    private final LocalDate birth_date;
    private final Set<Integer> friendList;
    private final Set<Integer> friendRequestList;

    @JsonCreator
    public User(String email, String login, String nickname, LocalDate birth_date) {
        this.email = email;
        this.login = login;
        if (nickname == null || nickname.isBlank()) {
            this.nickname = login;
        } else {
            this.nickname = nickname;
        }
        this.birth_date = birth_date;
        this.friendList = new HashSet<>();
        this.friendRequestList = new HashSet<>();
    }

    @Builder(toBuilder = true)
    public User(int id, String email, String login, String nickname,
                LocalDate birth_date, Set<Integer> friendList, Set<Integer> friendRequestList) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.nickname = nickname;
        this.birth_date = birth_date;
        this.friendList = friendList;
        this.friendRequestList = friendRequestList;
    }
}
