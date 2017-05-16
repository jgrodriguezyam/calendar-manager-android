package com.binarium.calendarmanager.dto.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class ChangeUserPasswordRequest {
    @SerializedName("UserName")
    private String userName;
    @SerializedName("OldPassword")
    private String OldPassword;
    @SerializedName("NewPassword")
    private String newPassword;

    public ChangeUserPasswordRequest() {
    }

    public ChangeUserPasswordRequest(String userName, String oldPassword, String newPassword) {
        this.userName = userName;
        OldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}