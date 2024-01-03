package com.gustaff_well.best_restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "votes", nullable = false )
    @ColumnDefault("0")
    @Min(0)
    private Integer votes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Dish> dishes;

    @Override
    public String toString() {
        return "Restaurant{" +
                "votes=" + votes +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
