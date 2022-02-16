package com.alex.vis.voteApp.util;

import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.to.UserTo;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {

    public static User createUserFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getPassword(), Role.USER);
    }

}
