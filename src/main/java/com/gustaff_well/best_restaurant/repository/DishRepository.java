package com.gustaff_well.best_restaurant.repository;

import com.gustaff_well.best_restaurant.model.Dish;
import com.gustaff_well.best_restaurant.util.exception.DataConflictException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    Optional<Dish> get(int restaurantId, int id);

    default Dish getBelonged(int restaurantId, int id) {
        return get(restaurantId, id).orElseThrow(
                () -> new DataConflictException("Dish with id=" + id +
                        " is not exist or doesn't belong to Restaurant with id=" + restaurantId));
    }

    @Cacheable(value = "dishes")
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.restaurant.id")
    List<Dish> getAll(int restaurantId);
}
