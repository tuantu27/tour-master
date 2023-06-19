package com.example.tour.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO {

    private int month;
    private Long total;
    private Long totalPeople;

    public DataDTO(int month, Long total) {
        this.month = month;
        this.total = total;
    }



}
