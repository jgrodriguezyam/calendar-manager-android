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
    private static String TODAY_DATE = "TodayDate";

    public static void setContext(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void setUserId(int userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.commit();
    }

    public static void setUserFullName(String userFullName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_FULL_NAME_KEY, userFullName);
        editor.commit();
    }

    public static void setUserName(String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME_KEY, userName);
        editor.commit();
    }

    public static void setPassword(String passsword) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PASSWORD_KEY, passsword);
        editor.commit();
    }

    public static void setTodayDate(String todayDate) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TODAY_DATE, todayDate);
        editor.commit();
    }

    public static int getUserId() {
        return sharedPreferences.getInt(USER_ID_KEY, 0);
    }

    public static String getUserFullName() {
        return sharedPreferences.getString(USER_FULL_NAME_KEY, null);
    }

    public static String getUserName(){
        return sharedPreferences.getString(USER_NAME_KEY, null);
    }

    public static String getPassword() {
        return sharedPreferences.getString(USER_PASSWORD_KEY, null);
    }

    public static String getTodayDate() {
        return sharedPreferences.getString(TODAY_DATE, null);
    }

    public static void removeUserId() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID_KEY);
        editor.commit();
    }

    public static void removeUserFullName() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_FULL_NAME_KEY);
        editor.commit();
    }

    public static void removeUserName() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_NAME_KEY);
        editor.commit();
    }

    public static void removePassword() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_PASSWORD_KEY);
        editor.commit();
    }

    public static void removeTodayDate() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TODAY_DATE);
        editor.commit();
    }

    public static void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}