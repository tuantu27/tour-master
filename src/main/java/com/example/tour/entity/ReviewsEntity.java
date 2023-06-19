package com.example.tour.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="review")
public class ReviewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String content;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String fullName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="tourId")
    private ToursEntity tour;

}
