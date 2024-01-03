package com.gustaff_well.best_restaurant.web.restaurant;

import com.gustaff_well.best_restaurant.model.Restaurant;
import com.gustaff_well.best_restaurant.repository.RestaurantRepository;
import com.gustaff_well.best_restaurant.service.RestaurantService;
import com.gustaff_well.best_restaurant.web.AuthUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
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
import static com.gustaff_well.best_restaurant.validation.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class RestaurantController {

    static final String REST_URL = "/api/restaurant";

    private final Logger log = getLogger(RestaurantController.class);

    private RestaurantRepository restaurantRepository;

    private RestaurantService restaurantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id={}", id);
        restaurantRepository.deleteExisted(id);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant with id={}", id);
        return restaurantRepository.getExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurant");
        return restaurantRepository.findAll();
    }

    @GetMapping("/with_dishes")
    public List<Restaurant> getAllWithDishes() {
        log.info("get all restaurant with dishes");
        return restaurantRepository.getAllWithDishes();
    }

    @PutMapping("/vote_for/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void voteFor(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        log.info("vote for restaurant with id={}", restaurantId);
        restaurantService.voteFor(restaurantId, authUser.id(), LocalTime.of(10, 0));
    }
}
