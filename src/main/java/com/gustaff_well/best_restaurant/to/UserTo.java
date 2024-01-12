package com.gustaff_well.best_restaurant.to;

import com.gustaff_well.best_restaurant.HasIdAndEmail;
import com.gustaff_well.best_restaurant.validation.NoHtml;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserTo extends NamedTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml
    String email;

    @NotBlank
    @Size(min = 5, max = 128)
    String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserTo:" + id + '[' + email + ']';
    }
}
