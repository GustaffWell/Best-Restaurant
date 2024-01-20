package com.gustaff_well.best_restaurant.web.user_selection;

import com.gustaff_well.best_restaurant.model.Menu;
import com.gustaff_well.best_restaurant.service.UserSelectionService;
import com.gustaff_well.best_restaurant.to.MenuTo;
import com.gustaff_well.best_restaurant.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UserSelectionController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class UserSelectionController {

    static final String REST_URL = "api/user-selection";

    private UserSelectionService userSelectionService;

    @GetMapping
    public MenuTo getCurrentSelectedMenu(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get selected menu for user with id={}", authUser.id());
        return userSelectionService.getCurrentSelectedMenu(authUser);
    }
}
