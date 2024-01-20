package com.gustaff_well.best_restaurant.service;

import com.gustaff_well.best_restaurant.repository.MenuRepository;
import com.gustaff_well.best_restaurant.util.MenuUtil;
import com.gustaff_well.best_restaurant.util.exception.VoteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.gustaff_well.best_restaurant.web.menu.MenuTestData.*;
import static com.gustaff_well.best_restaurant.web.user.UserTestData.user1;
import static com.gustaff_well.best_restaurant.web.user.UserTestData.user2;

@SpringBootTest
@ActiveProfiles("test")
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRepository menuRepository;

    @Test
    @Transactional
    void vote() {
        menuService.vote(MENU_1_ID, user1, BEFORE_11);
        Assertions.assertEquals(1, MenuUtil.createMenuTo(menuRepository.getExisted(MENU_1_ID)).getVotes());
        Assertions.assertThrows(VoteException.class, () -> menuService.vote(MENU_1_ID, user1, BEFORE_11));

        menuService.vote(MENU_1_ID, user2, AFTER_11);
        Assertions.assertEquals(2, MenuUtil.createMenuTo(menuRepository.getExisted(MENU_1_ID)).getVotes());
        Assertions.assertThrows(VoteException.class, () -> menuService.vote(MENU_1_ID, user2, AFTER_11));
    }
}
