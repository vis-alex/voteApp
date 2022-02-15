package com.alex.vis.voteApp.service;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.to.UserTo;

import java.util.List;

public interface UserService {
    List<UserTo> getAll();

    UserTo get(int id);
}
