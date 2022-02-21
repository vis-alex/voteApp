package com.alex.vis.voteApp.util;

import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.to.UserTo;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserUtil {

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    public static User prepareToUpdate(User oldUser, User newUser) {
        oldUser.setPassword(newUser.getPassword());
        return null;
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getPassword());
    }
}
