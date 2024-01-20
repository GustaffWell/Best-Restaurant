package com.gustaff_well.best_restaurant.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode
public class SavingMenuTo {

    @NotNull
    LocalDate localDate;

    @NotNull
    int restaurantId;

    @Override
    public String toString() {
        return "SavingMenuTo{" +
                "localDate=" + localDate +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
