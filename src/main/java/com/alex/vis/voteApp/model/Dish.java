package com.alex.vis.voteApp.model;

import com.alex.vis.voteApp.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dishes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = {"name", "price"}, callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Dish extends AbstractBaseEntity implements HasId{

    @Column(name = "name")
    @NotBlank
    @Size(min = 2, max = 50)
    @NoHtml
    private String name;

    @Column(name = "price")
    @Min(1)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(Integer id, String name, Integer price) {
        super(id);
        this.name = name;
        this.price = price;
    }
}
