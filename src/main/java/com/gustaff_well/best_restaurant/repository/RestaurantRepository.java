package com.gustaff_well.best_restaurant.repository;

import com.gustaff_well.best_restaurant.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

}
