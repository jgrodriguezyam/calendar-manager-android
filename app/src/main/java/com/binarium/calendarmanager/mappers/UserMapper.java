package com.binarium.calendarmanager.mappers;

import com.binarium.calendarmanager.dto.user.UserResponse;
import com.binarium.calendarmanager.viewmodels.user.User;

/**
 * Created by jrodriguez on 02/06/2017.
 */

public class UserMapper {
    public static User toUser(UserResponse userResponse) {
        User user = new User();
        user.setId(userResponse.getId());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setGenderType(userResponse.getGenderType());
        user.setEmail(userResponse.getEmail());
        user.setCellNumber(userResponse.getCellNumber());
        user.setUserName(userResponse.getUserName());
        user.setPassword(userResponse.getPassword());
        user.setPublicKey(userResponse.getPublicKey());
        user.setBadge(userResponse.getBadge());
        user.setDeviceId(userResponse.getDeviceId());
        return user;
    }
}
