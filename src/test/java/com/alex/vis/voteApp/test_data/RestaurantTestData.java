package com.alex.vis.voteApp.test_data;

import com.alex.vis.voteApp.MatcherFactory;
import com.alex.vis.voteApp.model.Restaurant;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");

    public static final int FIRST_RESTAURANT_ID = 100003;
    public static final int SECOND_RESTAURANT_ID = 100004;
    public static final int THIRD_RESTAURANT_ID = 100005;


    public static final Restaurant firstRestaurant = new Restaurant(FIRST_RESTAURANT_ID, "Shoko");
    public static final Restaurant secondRestaurant = new Restaurant(SECOND_RESTAURANT_ID, "Japan");
    public static final Restaurant thirdRestaurant = new Restaurant(THIRD_RESTAURANT_ID, "BurgerKing");

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(firstRestaurant.getId(), firstRestaurant.getName());
        updated.setName("UpdatedName");
        return updated;
    }

}
