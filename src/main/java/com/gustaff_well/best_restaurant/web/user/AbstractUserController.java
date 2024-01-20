package com.gustaff_well.best_restaurant.web.user;

import com.gustaff_well.best_restaurant.model.User;
import com.gustaff_well.best_restaurant.repository.UserRepository;
import com.gustaff_well.best_restaurant.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractUserController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public void delete(int id){
        log.info("delete User with id = {}", id);
        userRepository.deleteExisted(id);
    }

    public User get(int id) {
        log.info("get User with id = {}", id);
        return userRepository.getExisted(id);
    }
}
