package com.gustaff_well.best_restaurant;

import com.gustaff_well.best_restaurant.util.MenuPopulator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BestRestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(BestRestaurantApplication.class, args);
        MenuPopulator populator = MenuPopulator.get();
        populator.populateDb();
    }
}
