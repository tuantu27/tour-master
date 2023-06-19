package com.example.tour.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanysDTO {
    private Long companyId;
    private String companyName;
    private String companyCode;
    private String address;
    private String email;
    private int status;
    private Long accountId;
    private AccountsDTO accountsDTO;
    private List<PaymentsDTO> paymentsDTOS;
}
