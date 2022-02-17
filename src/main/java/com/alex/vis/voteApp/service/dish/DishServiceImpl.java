package com.alex.vis.voteApp.service.dish;

import com.alex.vis.voteApp.model.Dish;
import com.alex.vis.voteApp.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService{

    private final DishRepository dishRepository;

    @Override
    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    @Override
    public Dish get(int id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish create(Dish dish) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Dish dish) {

    }
}
