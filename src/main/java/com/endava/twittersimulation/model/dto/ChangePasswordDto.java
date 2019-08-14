package com.endava.twittersimulation.model.dto;

public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;

    public ChangePasswordDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
