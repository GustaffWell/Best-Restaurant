package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.model.*;
import com.gustaff_well.best_restaurant.repository.DishRepository;
import com.gustaff_well.best_restaurant.repository.MenuRepository;
import com.gustaff_well.best_restaurant.repository.RestaurantRepository;
import com.gustaff_well.best_restaurant.repository.UserSelectionRepository;
import com.gustaff_well.best_restaurant.to.MenuTo;
import com.gustaff_well.best_restaurant.to.SavingMenuTo;
import com.gustaff_well.best_restaurant.util.MenuUtil;
import com.gustaff_well.best_restaurant.util.exception.VoteException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserSelectionRepository userSelectionRepository;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    private final LocalTime DEAD_LINE = LocalTime.of(11, 0);

    @Transactional
    public Menu create(SavingMenuTo savingMenuTo, int restaurantId) {
        log.info("create menu");
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        Menu menu = new Menu(null, savingMenuTo.getLocalDate(), restaurant, new ArrayList<>(), new ArrayList<>());
        return menuRepository.save(menu);
    }

    @Transactional
    public void update(int id, Menu menu) {
        log.info("update menu with id={}", id);
        Menu updated = menuRepository.getExisted(id);
        updated.setDate(menu.getDate());
        updated.setRestaurant(menu.getRestaurant());
        menuRepository.save(updated);
    }

    @Transactional
    public List<MenuTo> getAllTos() {
        log.info("getAllTos");
        List<Menu> menuList = menuRepository.getAllWithDishes();
        List<MenuTo> menuToList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getDate().isEqual(LocalDate.now())){
                MenuTo menuTo = MenuUtil.createMenuTo(menu);
                menuToList.add(menuTo);
            }
        }
        return menuToList;
    }

    @Transactional
    public void vote(int menuId, User user, LocalTime currentTime) {
        log.info("vote for menu with id={}", menuId);
        LocalDate currentDate = LocalDate.now();
        Menu selectedMenu = menuRepository.getExisted(menuId);
        if (selectedMenu.getDate().isEqual(currentDate)) {
            UserSelection userSelection = userSelectionRepository.findByUserIdAndDate(user.id());
            List<User> selectedMenuUsers = selectedMenu.getUsers();
            if (userSelection == null) {
                selectedMenuUsers.add(user);
                userSelection = new UserSelection(null, user.id(), currentDate, menuId);
                userSelectionRepository.save(userSelection);
            } else if (currentTime.isBefore(DEAD_LINE) && userSelection.getMenuId() != selectedMenu.getId()) {
                Menu oldSelectedMenu = menuRepository.getExisted(userSelection.getMenuId());
                oldSelectedMenu.getUsers().remove(user);
                selectedMenuUsers.add(user);
                userSelection.setMenuId(selectedMenu.getId());
            } else {
                throw new VoteException("You can no longer vote for this restaurant today");
            }
        } else {
            throw new VoteException("Сhoose menu for today");
        }
    }

    @Transactional
    public void addDishFromDb(int id, int dishId) {
        log.info("add dish with id={} to menu with id={}", dishId, id);
        Menu menu = menuRepository.getExisted(id);
        Dish dish = dishRepository.getExisted(dishId);
        menu.getDishes().add(dish);
    }

    @Transactional
    public void addDishList(List<Dish> dishes, int id) {
        log.info("add dish list to menu with id={}", id);
        List<Dish> createdDishes = dishRepository.saveAll(dishes);
        Menu menu = menuRepository.getExisted(id);
        menu.getDishes().addAll(createdDishes);
    }
}
