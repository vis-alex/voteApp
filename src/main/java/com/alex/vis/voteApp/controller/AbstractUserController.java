package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.service.user.UserService;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    protected List<User> getAll() {
        log.info("getAll");
        return userService.getAll();
    }

    protected User get(int id) {
        log.info("get {}", id);
        return userService.get(id);
    }

    protected User create(User user) {
        log.info("create {}", user);
        return userService.create(user);
    }

    protected void delete(int id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    protected void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        userService.update(user, id);
    }

    protected void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        userService.enable(id, enabled);
    }

    protected User getByName(String name) {
        log.info("get User by name={}", name);
        return userService.getByName(name);
    }

    protected void deleteByName(String name) {
        log.info("delete User by name={}", name);
        userService.deleteByName(name);
    }
}
