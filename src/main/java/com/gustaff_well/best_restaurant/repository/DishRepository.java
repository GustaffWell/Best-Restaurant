package com.gustaff_well.best_restaurant.repository;

import com.gustaff_well.best_restaurant.model.Dish;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

}
