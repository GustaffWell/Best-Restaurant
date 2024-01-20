package com.gustaff_well.best_restaurant.web.restaurant;

import com.gustaff_well.best_restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.gustaff_well.best_restaurant.web.restaurant.RestaurantTestData.*;
import static com.gustaff_well.best_restaurant.web.user.UserTestData.USER_1_ID;

@SpringBootTest
@ActiveProfiles("test")
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void voteFor() {
        restaurantService.voteFor(RESTAURANT_1_ID, USER_1_ID, AFTER_11);
        Assertions.assertEquals(1, restaurantRepository.getExisted(RESTAURANT_1_ID).getVotes());

        restaurantService.voteFor(RESTAURANT_2_ID, USER_1_ID, BEFORE_11);
        Assertions.assertEquals(1, restaurantRepository.getExisted(RESTAURANT_2_ID).getVotes());
        Assertions.assertEquals(0, restaurantRepository.getExisted(RESTAURANT_1_ID).getVotes());
    }
}
