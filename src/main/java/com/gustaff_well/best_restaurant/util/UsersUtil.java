package com.gustaff_well.best_restaurant.util;

import com.gustaff_well.best_restaurant.model.Role;
import com.gustaff_well.best_restaurant.model.User;
import com.gustaff_well.best_restaurant.to.UserTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail(), userTo.getPassword(), null, Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}