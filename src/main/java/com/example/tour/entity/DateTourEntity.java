package com.example.tour.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "date_tour")
public class DateTourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateTourId;
    private String startDate;
    private Long quantity;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tourId")
    private ToursEntity tour;

}
