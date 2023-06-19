package com.example.tour.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesDTO {
    private Long id;
    private AccountsDTO accountsDTO;
    private String role;
}
