package com.alex.vis.voteApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

//    @Id
//    @SequenceGenerator(name = "SEQ", sequenceName = "SEQ", allocationSize = 1, initialValue = START_SEQ)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
//    private Integer id;
}
