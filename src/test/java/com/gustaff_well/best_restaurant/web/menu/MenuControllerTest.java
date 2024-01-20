package com.gustaff_well.best_restaurant.web.menu;

import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.model.Restaurant;
import com.gustaff_well.best_restaurant.repository.MenuRepository;
import com.gustaff_well.best_restaurant.repository.RestaurantRepository;
import com.gustaff_well.best_restaurant.to.SavingMenuTo;
import com.gustaff_well.best_restaurant.util.JsonUtil;
import com.gustaff_well.best_restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.gustaff_well.best_restaurant.web.dish.DishTestData.*;
import static com.gustaff_well.best_restaurant.web.menu.MenuTestData.*;
import static com.gustaff_well.best_restaurant.web.user.UserTestData.ADMIN_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuControllerTest extends AbstractControllerTest {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        SavingMenuTo newMenuTo = new SavingMenuTo(LocalDate.of(2024, 1, 2), 1);
        ResultActions action = perform(MockMvcRequestBuilders.post("/api/admin/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuTo)));

        Restaurant restaurant = restaurantRepository.getExisted(newMenuTo.getRestaurantId());
        Menu newMenu = new Menu(null, newMenuTo.getLocalDate(), restaurant, new ArrayList<>(), new ArrayList<>());
        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuRepository.getExisted(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        SavingMenuTo invalid = new SavingMenuTo(null, null);
        perform(MockMvcRequestBuilders.post("/api/admin/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        addDihesTo1Menu();

        Menu updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put("/api/admin/menus/" + MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuRepository.getWithDishes(MENU_1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        addDihesTo1Menu();

        Menu invalid = MenuTestData.getUpdated();
        invalid.setDate(null);
        perform(MockMvcRequestBuilders.put("/api/admin/menus/" + MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithDishes() throws Exception {
        addDihesTo1Menu();

        perform(MockMvcRequestBuilders.get("/api/admin/menus/with-dishes/" + MENU_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/api/admin/menus/" + DISH_1_ID))
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(MENU_1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithDishes() throws Exception {
        addDishesTo1And2Menu();

        perform(MockMvcRequestBuilders.get("/api/admin/menus/with-dishes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1, menu2));
    }

    private void addDihesTo1Menu() throws Exception {
        perform(MockMvcRequestBuilders.put("/api/admin/menus/dish-list/" + MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(List.of(dish1, dish2))))
                .andExpect(status().isNoContent());
    }

    private void addDishesTo1And2Menu() throws Exception {
        addDihesTo1Menu();

        perform(MockMvcRequestBuilders.put("/api/admin/menus/dish-list/" + MENU_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(List.of(dish3, dish4))))
                .andExpect(status().isNoContent());
    }
}
