package com.alex.vis.voteApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Vote extends AbstractBaseEntity{

    @Column(name = "user_id")
    private int userId;

    @Column(name = "restaurant_id")
    private int restaurantId;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

}
