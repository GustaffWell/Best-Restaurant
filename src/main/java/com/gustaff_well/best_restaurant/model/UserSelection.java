package com.gustaff_well.best_restaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "selection")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSelection extends AbstractBaseEntity {

    @Column(name = "user_id")
    int userId;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "menu_id")
    int menuId;

    public UserSelection(Integer id, int userId, LocalDate date, int menuId) {
        super(id);
        this.userId = userId;
        this.date = date;
        this.menuId = menuId;
    }
}
