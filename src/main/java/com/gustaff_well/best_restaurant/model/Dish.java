package com.gustaff_well.best_restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dish", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "name"},
                name = "uc_restaurant_id_dish_name")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends AbstractNamedEntity{

    @Column(name = "price", nullable = false)
    @NotNull
    @Min(0)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
