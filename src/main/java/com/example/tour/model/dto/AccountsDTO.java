package com.example.tour.model.dto;


import com.example.tour.entity.AccountsEntity;
import com.example.tour.entity.RoleEntity;
import com.example.tour.entity.ToursEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDTO {

    private Long accountId;
    private String userName;
    private String passWord;
    private List<RolesDTO> roles;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int status;
    private List<ToursDTO> toursDTOS;
    private CompanysDTO companysDTO;
    private List<RolesDTO> rolesDTOS;


}
