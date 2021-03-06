package com.binarium.calendarmanager.dto.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class UserRequest {
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

    public UserRequest() {
    }

    public UserRequest(int id, String firstName, String lastName, int genderType, String email, long cellNumber, String userName, String password, String publicKey, String badge, String deviceId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderType = genderType;
        this.email = email;
        this.cellNumber = cellNumber;
        this.userName = userName;
        this.password = password;
        this.publicKey = publicKey;
        this.badge = badge;
        this.deviceId = deviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGenderType() {
        return genderType;
    }

    public void setGenderType(int genderType) {
        this.genderType = genderType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(long cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}