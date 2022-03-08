package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.service.user.UserService;
import com.alex.vis.voteApp.validation.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = AdminController.ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Admin")
public class AdminController {
    static final String ADMIN_URL = "/admin/users";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    @GetMapping()
    @Operation(summary = "Get all users", responses = {
            @ApiResponse(description = "Get users success", responseCode = "200",
                   content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class)))
    })
    public List<User> getAll() {
        log.info("getAll");
        ValidationUtil.checkRoleAdmin();
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user", responses = {
            @ApiResponse(description = "Get user success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content)
    })
    public User get(@PathVariable int id) {
        log.info("get {}", id);
        ValidationUtil.checkRoleAdmin();
        return userService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        ValidationUtil.checkRoleAdmin();
        userService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        log.info("create {}", user);
        ValidationUtil.checkRoleAdmin();
        Assert.notNull(user, "user must not be null");
        ValidationUtil.checkNew(user);
        return userService.create(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        ValidationUtil.checkRoleAdmin();
        Assert.notNull(user, "user must not be null");
        userService.update(user, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        ValidationUtil.checkRoleAdmin();
        userService.enable(id, enabled);
    }
}
