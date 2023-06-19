package com.example.tour.entity;
import javax.persistence.*;

import com.example.tour.model.dto.CustomersDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="Customer")
public class CustomersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String number_id;
    private String address;
    private int status;

    @OneToMany(mappedBy = "customer")
    private List<BookingEntity> lstBooking;

    public static CustomersDTO toDto(CustomersEntity customersEntity){
        CustomersDTO customersDTO = new CustomersDTO();
        customersDTO.setCustomerId(customersEntity.getCustomerId());
        customersDTO.setFullName(customersEntity.getFullName());
        customersDTO.setCustomerId(customersEntity.getCustomerId());
        customersDTO.setAddress(customersEntity.getAddress());
        customersDTO.setEmail(customersEntity.getEmail());
        customersDTO.setPhoneNumber(customersEntity.getPhoneNumber());
        customersDTO.setStatus(customersEntity.getStatus());
        customersDTO.setNumber_id(customersEntity.getNumber_id());
        return customersDTO;
    }


}
