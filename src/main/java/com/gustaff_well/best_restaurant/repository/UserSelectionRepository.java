package com.gustaff_well.best_restaurant.repository;

import com.gustaff_well.best_restaurant.model.UserSelection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserSelectionRepository extends BaseRepository<UserSelection> {

    @Query("SELECT us FROM UserSelection us WHERE us.userId=:userId AND us.date=CURRENT_DATE")
    UserSelection findByUserIdAndDate(int userId);
}
