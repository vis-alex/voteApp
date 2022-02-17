package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserController.USERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    static final String USERS_URL = "/users";

    private final UserService userService;

    @GetMapping()
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userService.get(id);
    }
//
//    @DeleteMapping
//    public void delete(@AuthenticationPrincipal ) {
//
//    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        userService.update(userTo);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody UserTo userTo) {
        User created = userService.create(userTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(USERS_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
