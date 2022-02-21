package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = UserController.USERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends  AbstractUserController{
    static final String USERS_URL = "/user";

    @GetMapping
    public User get(@AuthenticationPrincipal UserDetails authUser) {
        return super.getByName(authUser.getUsername());
    }

    @DeleteMapping()
    public void delete(@AuthenticationPrincipal UserDetails authUser) {
        super.deleteByName(authUser.getUsername());
    }

    @PostMapping()
    public ResponseEntity<User> register(@RequestBody User user) {
        User created = super.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(USERS_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @AuthenticationPrincipal UserDetails authUser) {
        super.updateByName(user, authUser.getUsername());
    }

}
