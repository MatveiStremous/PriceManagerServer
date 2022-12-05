package com.example.pricemanager.dto;

import com.example.pricemanager.entity.User;

import java.io.Serializable;

public class UserDto implements Serializable {
    private int id;
    private String login;
    private User.UserRole userRole = User.UserRole.USER_ROLE;
    private User.UserStatus userStatus = User.UserStatus.ACTIVE;

    public enum UserRole {
        USER_ROLE,
        ADMIN_ROLE
    }

    public enum UserStatus {
        ACTIVE,
        BANNED
    }

    public UserDto() {
    }

    public UserDto(int id, String login, User.UserRole userRole, User.UserStatus userStatus) {
        this.id = id;
        this.login = login;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    public UserDto(String login, User.UserRole userRole, User.UserStatus userStatus) {
        this.login = login;
        this.userRole = userRole;
        this.userStatus = userStatus;
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

    public User.UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(User.UserRole userRole) {
        this.userRole = userRole;
    }

    public User.UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(User.UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
