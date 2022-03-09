package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.service.user.UserService;
import com.alex.vis.voteApp.to.UserTo;
import com.alex.vis.voteApp.util.UserUtil;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ProfileController.USERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Tag(name="Profile")
public class ProfileController {
    static final String USERS_URL = "/profile/users";

    private final UserService userService;

    @Operation(summary = "Get profile", responses = {
            @ApiResponse(description = "Get profile success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content)
    })
    @GetMapping
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get {}", authUser.getId());
        return userService.get(authUser.getId());
    }

    @Operation(summary = "Delete profile", responses = {
            @ApiResponse(description = "Delete profile success", responseCode = "204", content = @Content)
    })
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete {}", authUser.getId());
        userService.delete(authUser.getId());
    }

    @Operation(summary = "Register profile", responses = {
            @ApiResponse(description = "Register profile success", responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(description = "User is null or user_id is not null", responseCode = "422", content = @Content)
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody UserTo userTo) {
        log.info("create {}", userTo);
        Assert.notNull(userTo, "user must not be null");
        ValidationUtil.checkNew(userTo);
        return userService.create(UserUtil.createNewFromTo(userTo));
    }

    @Operation(summary = "Update profile", responses = {
            @ApiResponse(description = "Update profile success", responseCode = "204", content = @Content),
            @ApiResponse(description = "UserTo is null or user_id is not consistent ", responseCode = "422", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("update {} with id={}", userTo, authUser.getId());
        Assert.notNull(userTo, "user must not be null");
        ValidationUtil.assureIdConsistent(userTo, authUser.getId());
        userService.update(userTo);
    }
}
