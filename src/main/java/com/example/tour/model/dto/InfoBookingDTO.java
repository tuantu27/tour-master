package com.example.tour.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoBookingDTO {
    private Long bookingId;
    private Timestamp bookingTime;
    private Long numberAdult;
    private Long numberChildren;
    private String email;
    private Long numberInfant;
    private String paymentMethod;
    private Long totalPrice;
    private Long tourId;
    private String startDate;
    private Long numberRest;
    private Long dateTourId;
    private String fullName;
    private String phoneNumber;
    private int gender;
    private Timestamp dob; //date of birth
    private String address;
    private String number_id;
    private int status;
}
