package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.Dish;
import com.alex.vis.voteApp.service.dish.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = DishController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    static final String DISH_URL = "/dishes";

    private final DishService dishService;

    @GetMapping
    public List<Dish> getAll() {
        return dishService.getAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        return dishService.get(id);
    }
}
