package com.gustaff_well.best_restaurant.to;

import com.gustaff_well.best_restaurant.validation.NoHtml;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class DishTo {

    @NotBlank
    @Size(min = 2, max = 128)
    @NoHtml
    String name;

    @NotNull
    @Min(0)
    int price;

    public DishTo(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "DishTo:" + name + '[' + price + ']';
    }
}
