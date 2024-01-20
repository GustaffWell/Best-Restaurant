package com.gustaff_well.best_restaurant.web.menu;

import com.gustaff_well.best_restaurant.model.Dish;
import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.repository.MenuRepository;
import com.gustaff_well.best_restaurant.service.MenuService;
import com.gustaff_well.best_restaurant.to.MenuTo;
import com.gustaff_well.best_restaurant.to.SavingMenuTo;
import com.gustaff_well.best_restaurant.web.AuthUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

import static com.gustaff_well.best_restaurant.validation.ValidationUtil.assureIdConsistent;

@RestController
@AllArgsConstructor
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MenuController {

    static final String REST_URL = "/api";

    private MenuRepository menuRepository;
    private MenuService menuService;

    @PostMapping(value = "/admin/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody SavingMenuTo savingMenuTo) {
        log.info("create menu {}", savingMenuTo);
        Menu created = menuService.create(savingMenuTo, savingMenuTo.getRestaurantId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/admin/menus/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/admin/menus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody Menu menu) {
        log.info("update menu {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        menuService.update(id, menu);
    }

    @GetMapping("/admin/menus/with-dishes/{id}")
    public Menu getWithDishes(@PathVariable int id) {
        log.info("get menu with dishes with id={}", id);
        return menuRepository.getWithDishes(id);
    }

    @DeleteMapping("/admin/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu with id={}", id);
        menuRepository.deleteExisted(id);
    }

    @GetMapping(value = "/admin/menus/with-dishes")
    public List<Menu> getAllWithDishes() {
        log.info("get all menus with dishes");
        return menuRepository.getAllWithDishes();
    }

    @GetMapping(value = "/admin/menus")
    public List<Menu> getAll() {
        log.info("get all menus");
        return menuRepository.findAll();
    }

    @GetMapping(value = "/menus/with-dishes")
    public List<MenuTo> getAllTosWithDishes() {
        log.info("get all menus TO with dishes");
        return menuService.getAllTos();
    }

    @PutMapping(value = "/menus/vote/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("vote user with id={} for menu with id={}", authUser.id(), id);
        menuService.vote(id, authUser.getUser(), LocalTime.now());
    }

    @PutMapping(value = "/admin/menus/{id}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addDishFromDb(@PathVariable int id, @PathVariable int dishId) {
        log.info("add dish with id={} top menu with id={} from db", dishId, id);
        menuService.addDishFromDb(id, dishId);
    }

    @PutMapping(value = "/admin/menus/dish-list/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addDishList(@Valid @RequestBody List<Dish> dishes, @PathVariable int id) {
        log.info("add dish list to menu with id={}", id);
        menuService.addDishList(dishes, id);
    }
}
