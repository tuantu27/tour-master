package com.example.tour.entity;


import javax.persistence.*;

import com.example.tour.model.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Timestamp bookingTime;
    private Long numberAdult;
    private Long numberChildren;
    private Long numberInfant;
    private String paymentMethod;
    private Long totalPrice;
    private String startDate;
    private int status;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customerId")
    private CustomersEntity customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tourId")
    private ToursEntity tour;

//    public static BookingDTO toDto(BookingEntity bookingEntity){
//
//
//    }
}
