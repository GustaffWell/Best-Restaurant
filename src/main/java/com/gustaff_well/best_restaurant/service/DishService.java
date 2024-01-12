package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.model.Dish;
import com.gustaff_well.best_restaurant.repository.DishRepository;
import com.gustaff_well.best_restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Dish save(int restaurantId, Dish dish) {
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return dishRepository.save(dish);
    }
}
