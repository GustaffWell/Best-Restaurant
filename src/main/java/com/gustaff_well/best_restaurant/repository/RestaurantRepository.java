package com.gustaff_well.best_restaurant.repository;

import com.gustaff_well.best_restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Modifying
    @Query("UPDATE Restaurant r SET r.votes = r.votes + 1 WHERE r.id=:id")
    void addVote(int id);

    @Modifying
    @Query("UPDATE Restaurant r SET r.votes = r.votes - 1 WHERE r.id=:id")
    void takeVoteAway(int id);

    @Query("SELECT r FROM Restaurant r WHERE r.date=:date")
    List<Restaurant> findAllForDate(LocalDate date);
}
