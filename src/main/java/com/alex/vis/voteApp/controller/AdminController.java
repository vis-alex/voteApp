package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.validation.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminController.ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractUserController{
    static final String ADMIN_URL = "/admin/users";

    @Override
    @GetMapping()
    public List<User> getAll() {
        ValidationUtil.checkRoleAdmin();
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        ValidationUtil.checkRoleAdmin();
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        ValidationUtil.checkRoleAdmin();
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody User user) {
        ValidationUtil.checkRoleAdmin();

        User created = super.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        ValidationUtil.checkRoleAdmin();
        super.update(user, id);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        ValidationUtil.checkRoleAdmin();
        super.enable(id, enabled);
    }
}
