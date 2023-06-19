package com.example.tour.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleTypeTourDTO {
    private Long multipleTypeTourId;
    private ToursDTO toursDTO;
    private TypeTourDTO typeTourDTO;
}
