package com.gustaff_well.best_restaurant.web.user;

import com.gustaff_well.best_restaurant.model.Role;
import com.gustaff_well.best_restaurant.model.User;
import com.gustaff_well.best_restaurant.util.JsonUtil;
import com.gustaff_well.best_restaurant.web.MatcherFactory;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password");
    public static final int ADMIN_ID = 1;
    public static final int USER_1_ID = 2;
    public static final int USER_2_ID = 3;
    public static final int GUEST_ID = 4;
    public static final int NOT_FOUND_ID = 100;
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String USER_1_MAIL = "user1@yandex.ru";
    public static final String USER_2_MAIL = "user2@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", null, Role.ADMIN, Role.USER);
    public static final User user1 = new User(USER_1_ID, "User1", USER_1_MAIL, "password", null, Role.USER);
    public static final User user2 = new User(USER_2_ID, "User2", USER_2_MAIL, "password", null, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest", null);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", null, Role.USER);
    }

    public static User getUpdated() {
        return new User(USER_1_ID, "User1Updated", USER_1_MAIL, "newPass", null, Role.USER);
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}
