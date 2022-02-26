package com.alex.vis.voteApp.service.restaurant;

import com.alex.vis.voteApp.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAll();

    Restaurant get(int id);

    Restaurant create(Restaurant restaurant);

    void delete(int id);

    void update(Restaurant restaurant, int id);

    int getVoteCount(int id);

    //TODO Solve problem N + 1 queries
}
