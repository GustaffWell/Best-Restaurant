package com.gustaff_well.best_restaurant.web.dish;

import com.gustaff_well.best_restaurant.model.Dish;
import com.gustaff_well.best_restaurant.web.MatcherFactory;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);
    public static final int DISH_1_ID = 1;
    public static final int DISH_2_ID = 2;
    public static final int DISH_3_ID = 3;
    public static final int DISH_4_ID = 4;
    public static final Dish dish1 = new Dish(DISH_1_ID, "Том Ям", 800);
    public static final Dish dish2 = new Dish(DISH_2_ID, "Пепперони", 500);
    public static final Dish dish3 = new Dish(DISH_3_ID, "Джонни Сильверхенд", 450);
    public static final Dish dish4 = new Dish(DISH_4_ID, "Bloody Marry", 550);

    public static Dish getNew() {
        return new Dish(null, "newDish", 1000);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1_ID, "updatedDish", 1500);
    }
}
