package com.alex.vis.voteApp.util;

import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserUtil {

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

}
