package com.example.tour.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long bookingId;
    private Timestamp bookingTime;
    private Long numberAdult;
    private Long numberChildren;
    private Long numberInfant;
    private String paymentMethod;
    private Long totalPrice;
    private int status;
    private String startDate;
    private String formatAllPrice;
    private CustomersDTO customersDTO;
    private String companyName1;
    private ToursDTO toursDTO;
}
