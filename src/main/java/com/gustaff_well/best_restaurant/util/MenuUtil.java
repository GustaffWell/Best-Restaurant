package com.gustaff_well.best_restaurant.util;

import com.gustaff_well.best_restaurant.model.Dish;
import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.model.Restaurant;
import com.gustaff_well.best_restaurant.to.DishTo;
import com.gustaff_well.best_restaurant.to.MenuTo;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MenuUtil {

    public static MenuTo createMenuTo(Menu menu) {
        List<DishTo> dishTos = new ArrayList<>();
        for (Dish dish : menu.getDishes()) {
            DishTo dishTo = new DishTo(dish.getName(), dish.getPrice());
            dishTos.add(dishTo);
        }
        Restaurant restaurant = menu.getRestaurant();
        int votes = menu.getUsers().size();
        return new MenuTo(restaurant.id(), restaurant.getName(), dishTos, votes);
    }
}
