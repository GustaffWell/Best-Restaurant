package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.model.UserSelection;
import com.gustaff_well.best_restaurant.repository.MenuRepository;
import com.gustaff_well.best_restaurant.repository.UserSelectionRepository;
import com.gustaff_well.best_restaurant.to.MenuTo;
import com.gustaff_well.best_restaurant.util.MenuUtil;
import com.gustaff_well.best_restaurant.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserSelectionService {

    private UserSelectionRepository userSelectionRepository;
    private MenuRepository menuRepository;

    private static final MenuTo notSelected = new MenuTo(0, "not selected", null, 0);

    @Transactional
    public MenuTo getCurrentSelectedMenu(AuthUser authUser) {
        log.info("get current selected menu for user with id={}", authUser.id());
        UserSelection userSelection = userSelectionRepository.findByUserIdAndDate(authUser.id());
        if (userSelection != null) {
            Menu menu = menuRepository.getWithDishes(userSelection.getMenuId());
            return MenuUtil.createMenuTo(menu);
        }
        return notSelected;
    }
}
