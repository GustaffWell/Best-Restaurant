package com.gustaff_well.best_restaurant.model;

import com.gustaff_well.best_restaurant.validation.NoHtml;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbstractNamedEntity extends AbstractBaseEntity{

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    @NoHtml
    protected String name;

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}
