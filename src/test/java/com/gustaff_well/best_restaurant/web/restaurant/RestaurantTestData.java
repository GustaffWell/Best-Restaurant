package com.gustaff_well.best_restaurant.web.restaurant;

import com.gustaff_well.best_restaurant.model.Restaurant;
import com.gustaff_well.best_restaurant.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.gustaff_well.best_restaurant.web.dish.DishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final int RESTAURANT_1_ID = 1;
    public static final int RESTAURANT_2_ID = 2;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_1_ID, "Tokyo City");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_2_ID, "Afterlife");

    public static Restaurant getNew() {
        return new Restaurant(null, "newRest");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_1_ID, "newName");
    }

}
