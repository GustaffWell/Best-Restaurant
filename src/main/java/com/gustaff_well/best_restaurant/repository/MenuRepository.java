package com.gustaff_well.best_restaurant.repository;

import com.gustaff_well.best_restaurant.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu>{

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE")
    List<Menu> getAllWithDishes();

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.id=:id")
    Menu getWithDishes(int id);
}
