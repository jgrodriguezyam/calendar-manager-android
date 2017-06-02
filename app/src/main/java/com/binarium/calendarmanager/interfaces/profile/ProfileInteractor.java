package com.binarium.calendarmanager.interfaces.profile;

/**
 * Created by jrodriguez on 02/06/2017.
 */

public interface ProfileInteractor {
    void getUser(int userId, ProfileListener profileListener);
}
