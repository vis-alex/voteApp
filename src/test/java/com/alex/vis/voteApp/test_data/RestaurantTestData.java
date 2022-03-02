package com.alex.vis.voteApp.test_data;

import com.alex.vis.voteApp.MatcherFactory;
import com.alex.vis.voteApp.json.JsonUtil;
import com.alex.vis.voteApp.model.Restaurant;
import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;

import java.util.Collections;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int FIRST_RESTAURANT_ID = 100003;
    public static final int SECOND_RESTAURANT_ID = 100004;
    public static final int THIRD_RESTAURANT_ID = 100005;


//    public static final Restaurant first = new Restaurant(FIRST_RESTAURANT_ID, "Shoko")
//    public static final User admin = new User(ADMIN_ID, "Nik", "234",  Role.ADMIN, Role.USER);
//    public static final User secondUser = new User(SECOND_USER_ID, "Dick", "345", Role.USER);
//
//    public static String jsonWithPassword(User user, String password) {
//        return JsonUtil.writeAdditionProps(user, "password", password);
//    }
//
//    public static User getNew() {
//        return new User(null, "New", "newPass",  Role.USER);
//    }
//
//    public static User getUpdated() {
//        User updated = new User(user);
//        updated.setName("UpdatedName");
//        updated.setPassword("newPass");
//        updated.setEnabled(false);
//        updated.setRoles(Collections.singletonList(Role.ADMIN));
//        return updated;
//    }
//
}
