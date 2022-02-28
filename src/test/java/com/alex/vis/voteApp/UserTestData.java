package com.alex.vis.voteApp;

import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password");

    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "Vik", "123",  Role.USER);
    public static final User admin = new User(ADMIN_ID, "Nik", "234",  Role.ADMIN, Role.USER);

}
