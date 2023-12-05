package com.example.demo.persistence;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @ManyToMany(mappedBy = "participants")
    private Set<Conversation> conversations;
    @ManyToMany(mappedBy = "participants2")
    private Set<Conversation> conversations2;
    @Basic
    @Column(name = "nickname")
    private String nickname;
    @Basic
    @Column(name = "login_username")
    private String loginUsername;
    @Lob
    @Column(name = "password_hash")
    private String passwordHash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(nickname, user.nickname) && Objects.equals(loginUsername, user.loginUsername) && Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, loginUsername, passwordHash);
    }
}
