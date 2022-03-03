package com.alex.vis.voteApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Vote extends AbstractBaseEntity implements HasId{

    @Column(name = "user_id")
    @NotNull
    @Range(min = 0)
    private Integer userId;

    @Column(name = "restaurant_id")
    @NotNull
    @Range(min = 0)
    private Integer restaurantId;

    @Column(name = "date_time")
    private LocalDateTime dateTime;


    public Vote(Integer id, Integer userId, Integer restaurantId) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.dateTime = LocalDateTime.now();
    }

    public Vote(Integer userId, Integer restaurantId) {
        this(null, userId, restaurantId);
    }

    public Vote(Integer id, Integer userId, Integer restaurantId, LocalDateTime dateTime) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.dateTime = dateTime;
    }
}
