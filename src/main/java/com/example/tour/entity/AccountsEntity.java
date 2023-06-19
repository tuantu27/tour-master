package com.example.tour.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.example.tour.model.dto.AccountsDTO;
import com.example.tour.model.dto.RolesDTO;
import com.example.tour.model.dto.ToursDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class AccountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Column(unique = true)
    @NotEmpty(message = "{not.empty}")
    private String username;
    @NotEmpty(message = "{not.empty}")

    private String password;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date updateDate;

    private int status;
    @OneToMany(mappedBy = "account")
    private List<ToursEntity> toursEntityList;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId")
    private CompanysEntity company;
    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<RoleEntity> roles;

    public static AccountsDTO toDto(AccountsEntity accountsEntity){
        AccountsDTO accountsDTO = new AccountsDTO();
        accountsDTO.setAccountId(accountsDTO.getAccountId());
        accountsDTO.setUserName(accountsEntity.getUsername());
        accountsDTO.setPassWord(accountsEntity.getPassword());
        //accountsDTO.setRoles(accountsEntity.getRoles());
        accountsDTO.setCreatedAt((Timestamp) accountsEntity.getCreateDate());
        accountsDTO.setUpdatedAt((Timestamp) accountsEntity.getUpdateDate());
        List<ToursDTO> toursDTOS = new ArrayList<>();
        accountsEntity.getToursEntityList().stream().forEach(e ->{
            ToursDTO toursDTO = new ToursDTO();
            toursDTO.setTourId(e.getTourId());
            toursDTO.setTourName(e.getTourName());
            toursDTO.setStartDate(e.getStartDate());
            toursDTO.setDuration(e.getDuration());
            toursDTO.setDescription(e.getDescription());
            toursDTO.setPriceAdult(e.getPriceAdult());
            toursDTO.setPriceChildren(e.getPriceChildren());
            toursDTO.setPriceInfant(e.getPriceInfant());
            toursDTO.setStatus(e.getStatus());
            toursDTOS.add(toursDTO);
        });
        accountsDTO.setToursDTOS(toursDTOS);
        List<RolesDTO> rolesDTOS = new ArrayList<>();
        accountsEntity.getRoles().stream().forEach(e ->{
            RolesDTO rolesDTO = new RolesDTO();
            rolesDTO.setId(e.getId());
            rolesDTO.setRole(e.getRole());
          //  rolesDTO.setAccountsDTO(e.getUser());

            rolesDTOS.add(rolesDTO);
        });
        accountsDTO.setRolesDTOS(rolesDTOS);
        return accountsDTO;

    };

}