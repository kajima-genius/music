package com.example.music.backend.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity()
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
    private Role role = Role.USER;

    private Date dateOfBirth;

    private boolean enabled;

    public User(String username, Gender gender, String email, String password, String strDate) {
        this.enabled = false;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.dateOfBirth = UserUtil.parseDate(strDate);
    }
}
