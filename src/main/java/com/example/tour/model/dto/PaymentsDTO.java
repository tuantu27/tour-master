package com.example.tour.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsDTO {
    private Long paymentId;
    private String paymentMethod;
    private CompanysDTO companysDTO;
}
