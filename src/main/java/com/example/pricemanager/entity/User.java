package com.example.pricemanager.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private UserRole userRole = UserRole.USER_ROLE;

    enum UserRole {
        USER_ROLE,
        ADMIN_ROLE
    }

    public User(int id, String login, String password, UserRole userRole) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, UserRole userRole) {
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}

