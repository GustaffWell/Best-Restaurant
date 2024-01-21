package com.gustaff_well.best_restaurant.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "restaurant_id"},
        name = "uc_date_restaurant_id")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends AbstractBaseEntity{

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Restaurant restaurant;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "menu_dish",
                joinColumns = {@JoinColumn(name = "menu_id")},
                inverseJoinColumns = {@JoinColumn(name = "dish_id")})
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Dish> dishes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "menu_users",
            joinColumns = {@JoinColumn(name = "menu_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private  List<User> users;

    public Menu(Integer id, LocalDate date, Restaurant restaurant, List<Dish> dishes, List<User> users) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.users = users;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "date=" + date +
                ", restaurant=" + restaurant +
                '}';
    }
}
