package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.model.User;
import com.gustaff_well.best_restaurant.repository.UserRepository;
import lombok.AllArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class SchedulerService {

    private final Logger log = getLogger(SchedulerService.class);

    private UserRepository userRepository;

    @Scheduled(cron = "@daily")
    @SchedulerLock(name = "SchedulerService_clearSelectedRestaurantForAllUsers")
    @Transactional
    public void clearSelectedRestaurantForAllUsers() {
        log.info("Daily update of the selected restaurant");
        List<User> allUsers = userRepository.findAll();
        allUsers.forEach(user -> {
            if (user.getSelectedRestaurant() != null) {
                user.setSelectedRestaurant(null);
            }
        });
        userRepository.saveAllAndFlush(allUsers);
    }
}
