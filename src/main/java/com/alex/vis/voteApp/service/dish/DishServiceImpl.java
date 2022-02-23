package com.alex.vis.voteApp.service.dish;

import com.alex.vis.voteApp.exception.NotFoundException;
import com.alex.vis.voteApp.model.Dish;
import com.alex.vis.voteApp.model.Restaurant;
import com.alex.vis.voteApp.repository.DishRepository;
import com.alex.vis.voteApp.repository.RestaurantRepository;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService{

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public List<Dish> getAll() {
        ValidationUtil.checkRoleAdmin();
        return dishRepository.findAll();
    }

    public List<Dish> getAllDishesForRestaurant(int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Not found restaurant with id=" + restaurantId));

        return dishRepository.getAllDishesForRestaurant(restaurantId);
    }
    //TODO fix this repository query

    @Override
    public Dish get(int id) {
        return ValidationUtil.checkNotFoundWithId(dishRepository.getById(id), id);
    }

    @Override
    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        ValidationUtil.checkRoleAdmin();
        ValidationUtil.checkNew(dish);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        ValidationUtil.checkNotFoundWithId(restaurant, restaurantId);

        dish.setRestaurant(restaurant);
        return dishRepository.save(dish);
    }

    @Override
    public void delete(int id) {
        ValidationUtil.checkRoleAdmin();
        dishRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Dish dish, int id, Integer restaurantId) {
        ValidationUtil.checkRoleAdmin();
        Assert.notNull(dish, "dish must not be null");
        ValidationUtil.assureIdConsistent(dish, id);

        Dish newDish = updateDish(get(id), dish);

        if (restaurantId != null) {
            Restaurant restaurant = restaurantRepository.getById(restaurantId);
            ValidationUtil.checkNotFoundWithId(restaurant, restaurantId);
            newDish.setRestaurant(restaurant);
        }

        dishRepository.save(newDish);
    }

    private static Dish updateDish(Dish oldDish, Dish newDish) {
        oldDish.setName(newDish.getName());
        oldDish.setPrice(newDish.getPrice());
        return oldDish;
    }

}
