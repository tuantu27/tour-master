package com.example.tour.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsDTO {
    private Long reviewId;
    private String fullName;
    private String content;
    private Long tourId;
    private Timestamp createAt;
    private Timestamp updateAt;
    private ToursDTO toursDTO;
}
