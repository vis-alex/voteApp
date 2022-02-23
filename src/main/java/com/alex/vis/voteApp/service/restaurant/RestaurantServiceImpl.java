package com.alex.vis.voteApp.service.restaurant;

import com.alex.vis.voteApp.model.Restaurant;
import com.alex.vis.voteApp.repository.RestaurantRepository;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        ValidationUtil.checkRoleAdmin();
        ValidationUtil.checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(int id) {
        ValidationUtil.checkRoleAdmin();
        restaurantRepository.deleteById(id);
    }

    @Override
    public void update(Restaurant restaurant, int id) {
        ValidationUtil.checkRoleAdmin();
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }
}
