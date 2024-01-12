package com.gustaff_well.best_restaurant.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustaff_well.best_restaurant.HasIdAndEmail;
import com.gustaff_well.best_restaurant.validation.NoHtml;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractNamedEntity implements HasIdAndEmail {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 128)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "selected_restaurant")
    @JsonIgnore
    private Integer selectedRestaurant;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uc_user_role")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.selectedRestaurant, u.roles);
    }

    public User(Integer id, String name, String email, String password,
                Integer selectedRestaurant, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.selectedRestaurant = selectedRestaurant;
        setRoles(roles);
    }

    public User(Integer id, String name, String email, String password,
                Integer selectedRestaurant, Role... roles) {
        this(id, name, email, password, selectedRestaurant, Arrays.asList(roles));
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    @Override
    public String toString() {
        return "User:" + id + '[' + email + ']';
    }
}
