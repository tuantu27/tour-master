package com.example.tour.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomersDTO {
    private Long customerId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;
    private int status;
    private String number_id;
    private List<ReviewsDTO> reviewsDTOS;
    private List<BookingDTO> bookingDTOS;
}
