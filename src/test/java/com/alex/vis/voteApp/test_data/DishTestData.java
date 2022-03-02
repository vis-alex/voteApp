package com.alex.vis.voteApp.test_data;

import com.alex.vis.voteApp.MatcherFactory;
import com.alex.vis.voteApp.model.Dish;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int FIRST_DISH_ID = 100006;
    public static final int SECOND_DISH_ID = 100007;
    public static final int THIRD_DISH_ID = 100008;
    public static final int FOURTH_DISH_ID = 100009;

    public static final Dish firstDish = new Dish(FIRST_DISH_ID, "Soup", 100);
    public static final Dish secondDish = new Dish(SECOND_DISH_ID, "Water", 200);
    public static final Dish thirdDish = new Dish(THIRD_DISH_ID, "Bread", 300);
    public static final Dish fourthDish = new Dish(FOURTH_DISH_ID, "Muss", 400);

    public static final List<Dish> DISHES = List.of(firstDish, secondDish, thirdDish, fourthDish);

    public static Dish getNew() {
        return new Dish(null, "NewDish", 1000);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(firstDish.getId(),firstDish.getName(), firstDish.getPrice());
        updated.setName("UpdatedName");
        updated.setPrice(2000);
        return updated;
    }

}
