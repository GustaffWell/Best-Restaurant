package com.gustaff_well.best_restaurant.repository;

import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu>{

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes")
    List<Menu> getAllWithDishes();

    @Query("SELECT m FROM Menu m JOIN FETCH m.users")
    List<Menu> getAllWithUsers();

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.id=:id")
    Menu getWithDishes(int id);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.restaurant=:restaurant")
    void deleteAllByRestaurant(Restaurant restaurant);
}
