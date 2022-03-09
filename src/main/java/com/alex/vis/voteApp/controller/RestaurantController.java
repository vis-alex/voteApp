package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.Dish;
import com.alex.vis.voteApp.model.Restaurant;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.service.restaurant.RestaurantService;
import com.alex.vis.voteApp.service.vote.VoteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name="Restaurant")
public class RestaurantController {
    static final String RESTAURANT_URL = "/api/restaurants";

    private final RestaurantService restaurantService;

    @Operation(summary = "Get all restaurants", responses = {
            @ApiResponse(description = "Get restaurants success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Restaurant.class)))
    })
    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    @Operation(summary = "Get restaurant", responses = {
            @ApiResponse(description = "Get restaurant success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(description = "Restaurant not found", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }

    @Operation(summary = "Get vote count for restaurant", responses = {
            @ApiResponse(description = "Get vote count success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/{id}/count")
    public int getVoteCount(@PathVariable int id) {
        log.info("vote restaurant {}", id);
        return restaurantService.getVoteCount(id);
    }

    @Operation(summary = "Delete restaurant", responses = {
            @ApiResponse(description = "Delete restaurant success", responseCode = "204", content = @Content),
            @ApiResponse(description = "Restaurant not found", responseCode = "404", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
    }

    @Operation(summary = "Create restaurant", responses = {
            @ApiResponse(description = "Create restaurant success", responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(description = "Restaurant is null or restaurant_id is not null", responseCode = "422", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.create(restaurant);
        log.info("create restaurant {}", restaurant.getId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Update restaurant", responses = {
            @ApiResponse(description = "Update restaurant success", responseCode = "204", content = @Content),
            @ApiResponse(description = "Restaurant is null or restaurant_id is not consistent ", responseCode = "422", content = @Content)
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {}", id);
        restaurantService.update(restaurant ,id);
    }

}
