package com.alex.vis.voteApp.util;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.to.UserTo;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {
    public static UserTo createUserTo(User user) {
        return new UserTo(user.getId(), user.getName());
    }

    public static List<UserTo> createListUserTo(List<User> users) {
        return users.stream()
                .map(UserUtil::createUserTo)
                .collect(Collectors.toList());
    }
}
