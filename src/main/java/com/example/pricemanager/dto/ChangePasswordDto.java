package com.example.pricemanager.dto;

import java.io.Serializable;

public class ChangePasswordDto implements Serializable {
    private String login;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordDto() {
    }

    public ChangePasswordDto(String login, String oldPassword, String newPassword) {
        this.login = login;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
