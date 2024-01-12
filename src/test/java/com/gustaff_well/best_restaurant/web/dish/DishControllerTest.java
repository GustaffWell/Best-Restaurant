package com.gustaff_well.best_restaurant.web.dish;

import com.gustaff_well.best_restaurant.model.Dish;
import com.gustaff_well.best_restaurant.repository.DishRepository;
import com.gustaff_well.best_restaurant.util.JsonUtil;
import com.gustaff_well.best_restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.gustaff_well.best_restaurant.web.dish.DishController.REST_URL;
import static com.gustaff_well.best_restaurant.web.dish.DishTestData.*;
import static com.gustaff_well.best_restaurant.web.restaurant.RestaurantTestData.RESTAURANT_1_ID;
import static com.gustaff_well.best_restaurant.web.restaurant.RestaurantTestData.RESTAURANT_2_ID;
import static com.gustaff_well.best_restaurant.web.user.UserTestData.ADMIN_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + DISH_1_ID + "/for/" + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + DISH_1_ID + "/for/" + RESTAURANT_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + DISH_1_ID + "/for/" + RESTAURANT_2_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + DISH_1_ID + "/for/" + RESTAURANT_1_ID))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.get(RESTAURANT_1_ID, DISH_1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteDataConflict() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + DISH_1_ID + "/for/" + RESTAURANT_2_ID))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH_1_ID + "/for/" + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishRepository.getBelonged(RESTAURANT_1_ID, DISH_1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newMeal = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH  + "for/" + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)));

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newMeal.setId(newId);
        DISH_MATCHER.assertMatch(created, newMeal);
        DISH_MATCHER.assertMatch(dishRepository.getBelonged(RESTAURANT_1_ID, newId), newMeal);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH  + "for/" + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1, dish2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, -1);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH  + "for/" + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(DISH_1_ID, null, -1);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH_1_ID + "/for/" + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Dish invalid = new Dish(DISH_1_ID, "<script>alert(123)</script>", 200);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH_1_ID + "/for/" + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
