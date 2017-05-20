package com.binarium.calendarmanager.viewmodels.location;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public class Location {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private double radius;
    private int type;
    private String startDate;
    private String endDate;
    private String comment;
    private boolean isOwner;
    private boolean isChecked;

    public Location() {
    }

    public Location(int id, String name, double latitude, double longitude, double radius, int type, String startDate, String endDate, String comment, boolean isOwner, boolean isChecked) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
        this.isOwner = isOwner;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}