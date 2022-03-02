package com.alex.vis.voteApp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(of = {"name"}, callSuper = true)
public class Restaurant extends AbstractBaseEntity implements HasId{

    @Column(name = "name")
    @NotBlank
    @Size(max = 50)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    @OrderBy("name")
    @JsonManagedReference
    private Set<Dish> dishes;

}
