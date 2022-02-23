package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.to.UserTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = UserController.USERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends  AbstractUserController{
    static final String USERS_URL = "/user";

    @GetMapping
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        return super.get(authUser.getId());
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        super.delete(authUser.getId());
    }

    @PostMapping()
    public ResponseEntity<User> register(@RequestBody UserTo userTo) {
        User created = super.create(userTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(USERS_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        super.update(userTo, authUser.getId());
    }

}
