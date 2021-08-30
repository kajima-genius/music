package com.example.music.backend.user.domain;

import com.example.music.backend.playlist.domain.Playlist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Date dateOfBirth;
    private boolean enabled;

    public User(String username, Gender gender, String email, String password, Role role, String strDate) {
        this.enabled = false;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dateOfBirth = UserUtil.parseDate(strDate);
    }
}
