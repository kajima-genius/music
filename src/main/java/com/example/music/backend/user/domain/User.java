package com.example.music.backend.user.domain;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "users")
public class User {

    private String userName;

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

    @Column(name = "enabled")
    private boolean enabled;

    public User() {
    }

    public User(String userName, Gender gender, String email, String password, Role role, String strDate) {
        this.enabled = false;
        this.userName = userName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dateOfBirth = parseDate(strDate);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth.toString();
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private Date parseDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        try {
            Date date = formatter.parse(strDate);
            return date;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
