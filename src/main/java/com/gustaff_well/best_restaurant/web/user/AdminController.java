package com.gustaff_well.best_restaurant.web.user;

import com.gustaff_well.best_restaurant.model.User;
import com.gustaff_well.best_restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.gustaff_well.best_restaurant.validation.ValidationUtil.assureIdConsistent;
import static com.gustaff_well.best_restaurant.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractUserController {

    static final String REST_URL = "/api/admin/users";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("create {}", user);
        checkNew(user);
        User created = userRepository.prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        log.info("create {}", user);
        assureIdConsistent(user, id);
        userRepository.prepareAndSave(user);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete user with id={}", id);
        userService.deleteUser(userRepository.getExisted(id));
    }

    @Override
    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("grt user with id={}", id);
        return super.get(id);
    }

    @GetMapping("/by-email")
    public User getByEmail(@RequestParam String email) {
        log.info("grt user with email={}", email);
        return super.userRepository.getExistedByEmail(email);
    }

    @GetMapping
    public List<User> getAll() {
        log.info("getAll users");
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }
}
