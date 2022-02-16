package com.alex.vis.voteApp.service;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.to.UserTo;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(int id);

    User create(UserTo userTo);

    void delete(int id);

    void update(UserTo userTo);
}
