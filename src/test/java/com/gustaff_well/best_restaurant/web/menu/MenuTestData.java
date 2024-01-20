package com.gustaff_well.best_restaurant.web.menu;

import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.gustaff_well.best_restaurant.web.dish.DishTestData.*;
import static com.gustaff_well.best_restaurant.web.restaurant.RestaurantTestData.restaurant1;
import static com.gustaff_well.best_restaurant.web.restaurant.RestaurantTestData.restaurant2;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "users");

    public static final int MENU_1_ID = 1;
    public static final int MENU_2_ID = 2;
    public static final LocalTime BEFORE_11 = LocalTime.of(10, 30);
    public static final LocalTime AFTER_11 = LocalTime.of(11, 30);
    public static final Menu menu1 = new Menu(MENU_1_ID, LocalDate.now(), restaurant1, List.of(dish1, dish2), new ArrayList<>());
    public static final Menu menu2 = new Menu(MENU_2_ID, LocalDate.now(), restaurant2, List.of(dish3, dish4), new ArrayList<>());

    public static Menu getUpdated(){
        return new Menu(MENU_1_ID, LocalDate.of(2024, 1, 2), restaurant2, List.of(dish1, dish2), new ArrayList<>());
    }
}
