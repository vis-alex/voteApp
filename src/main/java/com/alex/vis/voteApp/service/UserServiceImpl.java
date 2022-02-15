package com.alex.vis.voteApp.service;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.repository.UserRepository;
import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public List<UserTo> getAll() {
        List<User> users = userRepository.findAll();
        return UserUtil.createListUserTo(users);
    }

    @Override
    public UserTo get(int id) {
        User user = userRepository.getById(id);
        System.out.println(user);
        return UserUtil.createUserTo(user);
    }
}
