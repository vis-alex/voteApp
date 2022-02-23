package com.alex.vis.voteApp.service.dish;

import com.alex.vis.voteApp.model.Dish;

import java.util.List;
import java.util.Set;

public interface DishService {
    List<Dish> getAll();

    Dish get(int id);

    Dish create(Dish dish, int id);

    void delete(int id);

    void update(Dish dish, int id, Integer restaurantId);

    Set<Dish> getAllDishesForRestaurant(int restaurantId);
}
