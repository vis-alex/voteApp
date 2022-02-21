package com.alex.vis.voteApp.service.user;

import com.alex.vis.voteApp.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(int id);

    User create(User user);

    void delete(int id);

    void update(User user, int id);

    void enable(int id, boolean enabled);

    User getByName(String name);

    void deleteByName(String name);

    void updateByName(User user, String name);
}
