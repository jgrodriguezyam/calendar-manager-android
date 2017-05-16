package com.binarium.calendarmanager.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by link_jorge on 13/12/2016.
 */

public class Preferences {

    public static final String PREFERENCE_NAME = "PreferencesFile";
    private static SharedPreferences sharedPreferences;

    private static String USER_ID_KEY = "UserId";
    private static String USER_FULL_NAME_KEY = "FullName";
    private static String USER_NAME_KEY = "UserName";
    private static String USER_PASSWORD_KEY = "Password";
    private static String LATITUDE_KEY = "Latitude";
    private static String LONGITUDE_KEY = "Longitude";
    private static String RADIUS_KEY = "Radius";
    private static String LOCATION_NAME_KEY = "LocationName";

    public static void setContext(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void setUserId(int userId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.commit();
    }

    public static void setUserFullName(String userFullName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_FULL_NAME_KEY, userFullName);
        editor.commit();
    }

    public static void setUserName(String userName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME_KEY, userName);
        editor.commit();
    }

    public static void setPassword(String passsword){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PASSWORD_KEY, passsword);
        editor.commit();
    }

    public static void setLatitude(double latitude){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String latitudeString = String.valueOf(latitude);
        editor.putString(LATITUDE_KEY, latitudeString);
        editor.commit();
    }

    public static void setLongitude(double longitude){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String longitudeString = String.valueOf(longitude);
        editor.putString(LONGITUDE_KEY, longitudeString);
        editor.commit();
    }

    public static void setRadius(double radius){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String radiusString = String.valueOf(radius);
        editor.putString(RADIUS_KEY, radiusString);
        editor.commit();
    }

    public static void setLocationName(String locationName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCATION_NAME_KEY, locationName);
        editor.commit();
    }

    public static int getUserId(){
        return sharedPreferences.getInt(USER_ID_KEY, 0);
    }

    public static String getUserFullName(){
        return sharedPreferences.getString(USER_FULL_NAME_KEY, null);
    }

    public static String getUserName(){
        return sharedPreferences.getString(USER_NAME_KEY, null);
    }

    public static String getPassword(){
        return sharedPreferences.getString(USER_PASSWORD_KEY, null);
    }

    public static double getLatitude(){
        String latitudeString = sharedPreferences.getString(LATITUDE_KEY, "0.0");
        double latitude = Double.parseDouble(latitudeString);
        return latitude;
    }

    public static double getLongitude(){
        String longitudeString = sharedPreferences.getString(LONGITUDE_KEY, "0.0");
        double longitude = Double.parseDouble(longitudeString);
        return longitude;
    }

    public static double getRadius(){
        String radiusString = sharedPreferences.getString(RADIUS_KEY, "0.0");
        double radius = Double.parseDouble(radiusString);
        return radius;
    }

    public static String getLocationName(){
        return sharedPreferences.getString(LOCATION_NAME_KEY, null);
    }

    public static void removeUserId(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID_KEY);
        editor.commit();
    }

    public static void removeUserFullName(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_FULL_NAME_KEY);
        editor.commit();
    }

    public static void removeUserName(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_NAME_KEY);
        editor.commit();
    }

    public static void removePassword(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_PASSWORD_KEY);
        editor.commit();
    }

    public static void removeLatitude(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LATITUDE_KEY);
        editor.commit();
    }

    public static void removeLongitude(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LONGITUDE_KEY);
        editor.commit();
    }

    public static void removeRadius(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(RADIUS_KEY);
        editor.commit();
    }

    public static void removeLocationName(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LOCATION_NAME_KEY);
        editor.commit();
    }

    public static void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
