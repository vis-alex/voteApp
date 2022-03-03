package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.Vote;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.service.vote.VoteService;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = VoteController.VOTE_URL)
@RequiredArgsConstructor
public class VoteController {
    static final String VOTE_URL = "/votes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService voteService;

    @PostMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Vote vote(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable(value = "id") int restaurantId) {
        log.info("vote for restaurant {} by user {}", restaurantId, authUser.getId());
        return voteService.vote(authUser.getId(), restaurantId);
    }

    @PostMapping(value = "/devote/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void devote(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable int id) {
        log.info("devote for restaurant {} by user {}", id, authUser.getId());
        voteService.devote(authUser.getId(), id);
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("get all votes");
        ValidationUtil.checkRoleAdmin();
        return voteService.getAll();
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote {} ", id);
        ValidationUtil.checkRoleAdmin();
        return voteService.get(id);
    }

}
