package com.alex.vis.voteApp.service.dish;

import com.alex.vis.voteApp.model.Dish;

import java.util.List;

public interface DishService {
    List<Dish> getAll();

    Dish get(int id);

    Dish create(Dish dish);

    void delete(int id);

    void update(Dish dish);
}
