package com.example.demo.model;

public class UserDataDTO {
    private long id;
    private String nickname;
    private String loginUsername;

    public UserDataDTO(long id, String nickname, String loginUsername) {
        this.id = id;
        this.nickname = nickname;
        this.loginUsername = loginUsername;
    }

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
}
