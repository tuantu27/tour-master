package com.example.tour.model.dto;

import com.example.tour.entity.ToursEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToursDTO {
    private Long tourId;
    private String tourName;
    private String startDate;
    private String duration; // khoang thoi gian
    private String description;
    private String imgName;
    private Long priceAdult;
    private Long priceChildren;
    private String formatPrice;
    private String formatPriceChil;
    private String formatPriceIn;
    private Long priceInfant;
    private String startDes;
    private int status;
    private Long quantity;
    private Long accountId;
    private AccountsDTO accountsDTO;
    private List<ReviewsDTO> reviewsDTOS;
    private List<BookingDTO> bookingDTOS;
    private List<MultipleTypeTourDTO> multipleTypeTourDTOS;


}
