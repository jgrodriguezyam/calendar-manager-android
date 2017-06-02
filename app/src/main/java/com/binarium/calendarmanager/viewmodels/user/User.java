package com.binarium.calendarmanager.viewmodels.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class User implements Parcelable {
    private int id;
    private String firstName;
    private String lastName;
    private int genderType;
    private String email;
    private long cellNumber;
    private String userName;
    private String password;
    private String publicKey;
    private String badge;
    private String deviceId;

    public User() {
    }

    public User(int id, String firstName, String lastName, int genderType, String email, long cellNumber, String userName, String password, String publicKey, String badge, String deviceId) {
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

    protected User(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        genderType = in.readInt();
        email = in.readString();
        cellNumber = in.readLong();
        userName = in.readString();
        password = in.readString();
        publicKey = in.readString();
        badge = in.readString();
        deviceId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(genderType);
        dest.writeString(email);
        dest.writeLong(cellNumber);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(publicKey);
        dest.writeString(badge);
        dest.writeString(deviceId);
    }
}