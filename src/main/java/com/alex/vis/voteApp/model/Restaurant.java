package com.alex.vis.voteApp.model;

import com.alex.vis.voteApp.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(of = {"name"}, callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Restaurant extends AbstractBaseEntity implements HasId{

    @Column(name = "name")
    @NotBlank
    @Size(min = 2, max = 50)
    @NoHtml
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    @OrderBy("name")
    @JsonManagedReference
    private Set<Dish> dishes = new HashSet<>();

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

}
