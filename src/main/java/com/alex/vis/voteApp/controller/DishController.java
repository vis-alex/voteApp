package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.Dish;
import com.alex.vis.voteApp.model.User;
import com.alex.vis.voteApp.service.dish.DishService;
import com.sun.istack.NotNull;
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
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = DishController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name="Dish")
public class DishController {

    static final String DISH_URL = "/api/dishes";

    private final DishService dishService;

    @Operation(summary = "Get all dishes", responses = {
            @ApiResponse(description = "Get dishes success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Dish.class)))
    })
    @GetMapping()
    public List<Dish> getAll() {
        log.info("get all dishes");
        return dishService.getAll();
    }

    @Operation(summary = "Get dish", responses = {
            @ApiResponse(description = "Get dish success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Dish.class))),
            @ApiResponse(description = "Dish not found", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish {} ", id);
        return dishService.get(id);
    }

    @Operation(summary = "Get all dishes for restaurant", responses = {
            @ApiResponse(description = "Get dishes success", responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Dish.class)))
    })
    @GetMapping("/restaurant/{id}")
    public Set<Dish> getAllDishesForRestaurant(@PathVariable(value = "id") int restaurantId) {
        log.info("get all dishes for restaurant {} ", restaurantId);
        return dishService.getAllDishesForRestaurant(restaurantId);
    }

    @Operation(summary = "Delete dish", responses = {
            @ApiResponse(description = "Delete dish success", responseCode = "204", content = @Content),
            @ApiResponse(description = "Dish not found", responseCode = "404", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish {} ", id);
        dishService.delete(id);
    }

    @Operation(summary = "Create dish", responses = {
            @ApiResponse(description = "Create dish success", responseCode = "201",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Dish.class))),
            @ApiResponse(description = "Dish is null or dish_id is not null", responseCode = "422", content = @Content),
            @ApiResponse(description = "Restaurant for dish not found", responseCode = "404", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish, @RequestParam int restaurant_id) {
        Dish created = dishService.create(dish, restaurant_id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("dishes/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Update dish", responses = {
            @ApiResponse(description = "Update dish success", responseCode = "204", content = @Content),
            @ApiResponse(description = "Dish is null or dish_id is not consistent ", responseCode = "422", content = @Content),
            @ApiResponse(description = "Restaurant for dish not found", responseCode = "404", content = @Content)
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @RequestParam(required = false) Integer restaurant_id) {
        log.info("update dish {} ", id);
        dishService.update(dish, id, restaurant_id);
    }

}
