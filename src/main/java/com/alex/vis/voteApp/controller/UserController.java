package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping()
    public List<UserTo> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserTo get(@PathVariable int id) {
        return userService.get(id);
    }
}
