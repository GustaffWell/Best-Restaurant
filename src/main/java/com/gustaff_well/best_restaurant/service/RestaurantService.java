package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.model.User;
import com.gustaff_well.best_restaurant.repository.RestaurantRepository;
import com.gustaff_well.best_restaurant.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public void voteFor(int restaurantId, int userId, LocalTime currentTime) {
        User user = userRepository.getExisted(userId);
        if (currentTime.isBefore(LocalTime.of(11, 0))) {
            Integer selectedRestaurantId = user.getSelectedRestaurant();
            if (selectedRestaurantId == null) {
                user.setSelectedRestaurant(restaurantId);
                userRepository.save(user);
                restaurantRepository.addVote(restaurantId);
            } else if (selectedRestaurantId != restaurantId) {
                user.setSelectedRestaurant(restaurantId);
                userRepository.save(user);
                restaurantRepository.addVote(restaurantId);
                restaurantRepository.takeVoteAway(selectedRestaurantId);
            }
        }
    }
}
