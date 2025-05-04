package com.example.accounts;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserProfile implements Serializable {

    @Id
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @Column(name = "password", nullable = false, length = 60)
    String password;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    String email;

    public UserProfile() {}

    public UserProfile(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
}
