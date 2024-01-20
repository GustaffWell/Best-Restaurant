package com.gustaff_well.best_restaurant.util;

import com.gustaff_well.best_restaurant.model.Dish;
import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.repository.DishRepository;
import com.gustaff_well.best_restaurant.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class MenuPopulator implements InitializingBean {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MenuRepository menuRepository;

    private static MenuPopulator instance;

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

    public static MenuPopulator get() {
        return instance;
    }

    public void populateDb() {
        Menu menuFor1Restaurant = menuRepository.getExisted(1);
        Dish dish1 = dishRepository.getExisted(1);
        Dish dish2 = dishRepository.getExisted(2);
        menuFor1Restaurant.setDishes(List.of(dish1, dish2));
        menuRepository.save(menuFor1Restaurant);

        Menu menuFor2Restaurant = menuRepository.getExisted(2);
        Dish dish3 = dishRepository.getExisted(3);
        Dish dish4 = dishRepository.getExisted(4);
        menuFor2Restaurant.setDishes(List.of(dish3, dish4));
        menuRepository.save(menuFor2Restaurant);
    }
}
