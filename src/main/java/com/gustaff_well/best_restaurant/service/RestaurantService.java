package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.model.Restaurant;
import com.gustaff_well.best_restaurant.model.User;
import com.gustaff_well.best_restaurant.repository.DishRepository;
import com.gustaff_well.best_restaurant.repository.RestaurantRepository;
import com.gustaff_well.best_restaurant.repository.UserRepository;
import com.gustaff_well.best_restaurant.util.exception.VoteException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    @Transactional
    public void voteFor(int restaurantId, int userId, LocalTime currentTime) {
        User user = userRepository.getExisted(userId);
        Integer selectedRestaurantId = user.getSelectedRestaurant();
        if (selectedRestaurantId == null) {
            user.setSelectedRestaurant(restaurantId);
            userRepository.save(user);
            restaurantRepository.addVote(restaurantId);
        } else if (currentTime.isBefore(LocalTime.of(11, 0)) && selectedRestaurantId != restaurantId) {
            user.setSelectedRestaurant(restaurantId);
            userRepository.save(user);
            restaurantRepository.addVote(restaurantId);
            restaurantRepository.takeVoteAway(selectedRestaurantId);
        } else {
            throw new VoteException("You can no longer vote for this restaurant today");
        }
    }

    public List<Restaurant> getAllWithDishes() {
        List<Restaurant> restaurants = restaurantRepository.findAllForDate(LocalDate.now());
        for (Restaurant restaurant : restaurants) {
            restaurant.setDishes(dishRepository.getAll(restaurant.id()));
        }
        return restaurants;
    }
}
