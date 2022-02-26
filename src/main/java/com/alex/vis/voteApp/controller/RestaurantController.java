package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.Restaurant;
import com.alex.vis.voteApp.security.AuthorizedUser;
import com.alex.vis.voteApp.service.restaurant.RestaurantService;
import com.alex.vis.voteApp.service.vote.VoteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String RESTAURANT_URL = "/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }

    @GetMapping("/{id}/count")
    public int getVoteCount(@PathVariable int id) {
        log.info("vote restaurant {}", id);
        return restaurantService.getVoteCount(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.create(restaurant);
        log.info("create restaurant {}", restaurant.getId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {}", id);
        restaurantService.update(restaurant ,id);
    }

}
