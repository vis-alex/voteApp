package com.alex.vis.voteApp.service.restaurant;

import com.alex.vis.voteApp.model.Restaurant;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAll();

    Restaurant get(int id);

    Restaurant create(UserDetails user, Restaurant restaurant);

    void delete(UserDetails user, int id);

    void update(Restaurant restaurant, int id);

    //TODO Solve problem N + 1 queries
}
