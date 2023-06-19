package com.example.tour.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DateTourDTO {
    private Long dateTourId;
    private String startDate;
    private Long quantity;
}
