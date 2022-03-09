package com.alex.vis.voteApp.service.dish;

import com.alex.vis.voteApp.exception.NotFoundException;
import com.alex.vis.voteApp.model.Dish;
import com.alex.vis.voteApp.model.Restaurant;
import com.alex.vis.voteApp.repository.DishRepository;
import com.alex.vis.voteApp.repository.RestaurantRepository;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService{

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    @Cacheable("dishes")
    public List<Dish> getAll() {
        ValidationUtil.checkRoleAdmin();
        return dishRepository.findAll();
    }

    @Transactional
    public Set<Dish> getAllDishesForRestaurant(int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Not found restaurant with id=" + restaurantId));

        return restaurant.getDishes();
    }

    @Override
    public Dish get(int id) {
        return dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found dish with id=" + id));
    }

    @Override
    @Transactional
    @CacheEvict(value = "dishes", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        ValidationUtil.checkRoleAdmin();
        ValidationUtil.checkNew(dish);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        ValidationUtil.checkNotFoundWithId(restaurant, restaurantId);

        dish.setRestaurant(restaurant);
        return dishRepository.save(dish);
    }

    @Override
    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id) {
        ValidationUtil.checkRoleAdmin();
        ValidationUtil.checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "dishes", allEntries = true)
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
