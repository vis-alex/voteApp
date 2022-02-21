package com.alex.vis.voteApp.security;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.util.UserUtil;

import java.io.Serial;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{

    @Serial
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getName(), user.getPassword(), user.isEnabled(),
                true, true, true, user.getRoles());

        setTo(UserUtil.asTo(user));
    }

    public int getId() {
        return userTo.getId();
    }

    public void setTo(UserTo newTo) {
        newTo.setPassword(null);
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
