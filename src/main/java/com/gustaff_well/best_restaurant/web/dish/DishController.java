package com.gustaff_well.best_restaurant.web.dish;

import com.gustaff_well.best_restaurant.service.DishService;
import com.gustaff_well.best_restaurant.model.Dish;
import com.gustaff_well.best_restaurant.repository.DishRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static com.gustaff_well.best_restaurant.validation.ValidationUtil.assureIdConsistent;
import static com.gustaff_well.best_restaurant.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    static final String REST_URL = "/api/admin/dishes";

    private DishRepository dishRepository;
    private DishService dishService;
    private CacheManager cacheManager;

    @PostMapping(value = "/for/{restaurantId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        evictAllCacheValues("dishes");
        log.info("create dish {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        Dish created = dishService.save(restaurantId, dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}/for/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @PathVariable int id, @Valid @RequestBody Dish dish) {
        evictAllCacheValues("dishes");
        log.info("update dish with id={} for restaurant with id={}", id, restaurantId);
        assureIdConsistent(dish, id);
        dishRepository.getBelonged(restaurantId, id);
        dishService.save(restaurantId, dish);
    }

    @GetMapping("/{id}/for/{restaurantId}")
    public ResponseEntity<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish with id={}", id);
        return ResponseEntity.of(dishRepository.get(restaurantId, id));
    }

    @GetMapping("/for/{restaurantId}")
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("getAll for restaurant with id={}", restaurantId);
        return dishRepository.getAll(restaurantId);
    }

    @DeleteMapping("{id}/for/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        evictAllCacheValues("dishes");
        log.info("delete dish with id={} for restaurant with id={}", id, restaurantId);
        Dish dish = dishRepository.getBelonged(restaurantId, id);
        dishRepository.delete(dish);
    }

    private void evictAllCacheValues(String cacheName) {
        Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
    }
}
