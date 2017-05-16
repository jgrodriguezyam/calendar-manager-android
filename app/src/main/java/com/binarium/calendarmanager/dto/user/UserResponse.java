package com.binarium.calendarmanager.dto.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class UserResponse {
    @SerializedName("Id")
    private int id;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("GenderType")
    private int genderType;
    @SerializedName("Email")
    private String email;
    @SerializedName("CellNumber")
    private long cellNumber;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String password;
    @SerializedName("PublicKey")
    private String publicKey;
    @SerializedName("Badge")
    private String badge;
    @SerializedName("DeviceId")
    private String deviceId;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGenderType() {
        return genderType;
    }

    public String getEmail() {
        return email;
    }

    public long getCellNumber() {
        return cellNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getBadge() {
        return badge;
    }

    public String getDeviceId() {
        return deviceId;
    }
}