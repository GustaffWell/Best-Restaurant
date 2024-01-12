package com.gustaff_well.best_restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "votes")
    @ColumnDefault("0")
    @Min(0)
    private Integer votes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    @Column(name = "date")
    @ColumnDefault("CURRENT_DATE")
    private LocalDate date;

    public Restaurant(Integer id, String name, Integer votes, LocalDate date) {
        super(id, name);
        this.votes = votes;
        this.date = date;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.id, restaurant.name, restaurant.votes, restaurant.date);
        this.dishes = List.copyOf(restaurant.dishes);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "votes=" + votes +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
