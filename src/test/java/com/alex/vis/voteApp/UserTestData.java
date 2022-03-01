package com.alex.vis.voteApp;

import com.alex.vis.voteApp.json.JsonUtil;
import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;

import java.util.Collections;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password");

    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;
    public static final int SECOND_USER_ID = 100002;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "Vik", "123",  Role.USER);
    public static final User admin = new User(ADMIN_ID, "Nik", "234",  Role.ADMIN, Role.USER);
    public static final User secondUser = new User(SECOND_USER_ID, "Dick", "345", Role.USER);

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }

    public static User getNew() {
        return new User(null, "New", "newPass",  Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

}
