package com.gustaff_well.best_restaurant.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends NamedTo{

    List<DishTo> menu;

    int votes;

    public MenuTo(Integer id, String name, List<DishTo> menu, int votes) {
        super(id, name);
        this.menu = menu;
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
