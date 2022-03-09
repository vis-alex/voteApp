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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = AdminController.ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Tag(name="Admin")
public class AdminController {
    static final String ADMIN_URL = "/admin/users";

    private final UserService userService;

    @Operation(summary = "Get all users", responses = {
            @ApiResponse(description = "Get users success", responseCode = "200",
                   content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class)))
    })
    @GetMapping()
    public List<User> getAll() {
        log.info("getAll");
        ValidationUtil.checkRoleAdmin();
        return userService.getAll();
    }

    @Operation(summary = "Get user", responses = {
            @ApiResponse(description = "Get user success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("get {}", id);
        ValidationUtil.checkRoleAdmin();
        return userService.get(id);
    }

    @Operation(summary = "Delete user", responses = {
            @ApiResponse(description = "Delete user success", responseCode = "204", content = @Content),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        ValidationUtil.checkRoleAdmin();
        userService.delete(id);
    }

    @Operation(summary = "Create user", responses = {
            @ApiResponse(description = "Create user success", responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(description = "User is null or user_id is not null", responseCode = "422", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        log.info("create {}", user);
        ValidationUtil.checkRoleAdmin();
        Assert.notNull(user, "user must not be null");
        ValidationUtil.checkNew(user);
        return userService.create(user);
    }

    @Operation(summary = "Update user", responses = {
            @ApiResponse(description = "Update user success", responseCode = "204", content = @Content),
            @ApiResponse(description = "User is null or user_id is not consistent ", responseCode = "422", content = @Content)
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        ValidationUtil.checkRoleAdmin();
        Assert.notNull(user, "user must not be null");
        userService.update(user, id);
    }

    @Operation(summary = "Enable/Disable user", responses = {
            @ApiResponse(description = "Enable/Disable user success", responseCode = "204", content = @Content)
    })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        ValidationUtil.checkRoleAdmin();
        userService.enable(id, enabled);
    }
}
