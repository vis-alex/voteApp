package com.alex.vis.voteApp.service.user;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.to.UserTo;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(int id);

    User create(User user);

    void delete(int id);

    void update(User user, int id);

    void enable(int id, boolean enabled);
}
