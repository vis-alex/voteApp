package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.service.user.UserService;
import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.util.UserUtil;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = UserController.USERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    static final String USERS_URL = "/user";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    @GetMapping
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get {}", authUser.getId());
        return userService.get(authUser.getId());
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete {}", authUser.getId());
        userService.delete(authUser.getId());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody UserTo userTo) {
        log.info("create {}", userTo);
        Assert.notNull(userTo, "user must not be null");
        ValidationUtil.checkNew(userTo);
        return userService.create(UserUtil.createNewFromTo(userTo));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("update {} with id={}", userTo, authUser.getId());
        Assert.notNull(userTo, "user must not be null");
        ValidationUtil.assureIdConsistent(userTo, authUser.getId());
        userService.update(userTo);
    }
}
