package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.model.User;
import com.gustaff_well.best_restaurant.repository.MenuRepository;
import com.gustaff_well.best_restaurant.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void deleteUser(User user) {
        log.info("delete user {}", user);
        List<Menu> menus = menuRepository.getAllWithUsers();
        User deleted = userRepository.getExisted(0);
        for (Menu menu : menus) {
            List<User> users = menu.getUsers();
            if (users.contains(user)) {
                users.set(users.indexOf(user), deleted);
            }
        }
        userRepository.delete(user);
    }
}
