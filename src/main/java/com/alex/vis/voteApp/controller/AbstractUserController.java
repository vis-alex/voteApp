package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.service.user.UserService;
import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.util.UserUtil;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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

    protected void delete(int id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    protected User create(User user) {
        log.info("create {}", user);
        Assert.notNull(user, "user must not be null");
        ValidationUtil.checkNew(user);
        return userService.create(user);
    }

    protected User create(UserTo userTo) {
        log.info("create {}", userTo);
        Assert.notNull(userTo, "user must not be null");
        ValidationUtil.checkNew(userTo);
        return userService.create(UserUtil.createNewFromTo(userTo));
    }

    protected void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        Assert.notNull(user, "user must not be null");
        userService.update(user, id);
    }

    protected void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        Assert.notNull(userTo, "user must not be null");
        ValidationUtil.assureIdConsistent(userTo, id);
        userService.update(userTo);
    }


    protected void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        userService.enable(id, enabled);
    }

}
