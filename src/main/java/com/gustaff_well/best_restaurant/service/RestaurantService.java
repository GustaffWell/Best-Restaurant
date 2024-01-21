package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.model.Restaurant;
import com.gustaff_well.best_restaurant.repository.MenuRepository;
import com.gustaff_well.best_restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class RestaurantService {

    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;

    @Transactional
    public void delete(int id) {
        log.info("delete restaurant with id={}", id);
        Restaurant restaurant = restaurantRepository.getExisted(id);
        menuRepository.deleteAllByRestaurant(restaurant);
        restaurantRepository.delete(restaurant);
    }
}
