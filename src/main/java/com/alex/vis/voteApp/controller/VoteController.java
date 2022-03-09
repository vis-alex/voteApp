package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.Restaurant;
import com.alex.vis.voteApp.model.Vote;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.service.vote.VoteService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = VoteController.VOTE_URL)
@RequiredArgsConstructor
@Slf4j
@Tag(name="Vote")
public class VoteController {
    static final String VOTE_URL = "/api/votes";

    private final VoteService voteService;

    @Operation(summary = "Vote for restaurant", responses = {
            @ApiResponse(description = "Vote for restaurant success", responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vote.class))),
            @ApiResponse(description = "Restaurant for voting is not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "This user have voted today already.", responseCode = "403", content = @Content)
    })
    @PostMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Vote vote(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable(value = "id") int restaurantId) {
        log.info("vote for restaurant {} by user {}", restaurantId, authUser.getId());
        return voteService.vote(authUser.getId(), restaurantId);
    }

    @Operation(summary = "Unvote for restaurant", responses = {
            @ApiResponse(description = "Unvote for restaurant success", responseCode = "204", content = @Content),
            @ApiResponse(description = "Restaurant for unvoting is not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "This user have not voted today.", responseCode = "403", content = @Content)
    })
    @PostMapping(value = "/unvote/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unVote(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable int id) {
        log.info("Unvote for restaurant {} by user {}", id, authUser.getId());
        voteService.unVote(authUser.getId(), id);
    }

    @Operation(summary = "Get all votes", responses = {
            @ApiResponse(description = "Get votes success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vote.class)))
    })
    @GetMapping
    public List<Vote> getAll() {
        log.info("get all votes");
        return voteService.getAll();
    }


    @Operation(summary = "Get vote", responses = {
            @ApiResponse(description = "Get vote success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vote.class))),
            @ApiResponse(description = "Vote not found", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote {} ", id);
        return voteService.get(id);
    }

}
