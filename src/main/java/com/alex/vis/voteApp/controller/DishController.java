package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.Dish;
import com.alex.vis.voteApp.service.dish.DishService;
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
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = DishController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    static final String DISH_URL = "/dishes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DishService dishService;

    @GetMapping()
    public List<Dish> getAll() {
        log.info("get all dishes");
        return dishService.getAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish {} ", id);
        return dishService.get(id);
    }

    @GetMapping("/restaurant/{id}")
    public Set<Dish> getAllDishesForRestaurant(@PathVariable(value = "id") int restaurantId) {
        log.info("get all dishes for restaurant {} ", restaurantId);
        return dishService.getAllDishesForRestaurant(restaurantId);
    }
    //TODO Send sorting with query

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish {} ", id);
        dishService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@RequestBody Dish dish, @RequestParam int restaurant_id) {
        Dish created = dishService.create(dish, restaurant_id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("dishes/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable int id, @RequestParam(required = false) Integer restaurant_id) {
        log.info("update dish {} ", id);
        dishService.update(dish, id, restaurant_id);
    }

}
