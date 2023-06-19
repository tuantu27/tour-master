package com.example.tour.service;

import com.example.tour.entity.CustomersEntity;
import com.example.tour.model.dto.CustomersDTO;
import com.example.tour.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService{
    @Autowired
    ICustomerRepository iCustomerRepository;

    @Override
    public Long saveCusomer(CustomersDTO customersDTO) {
        CustomersEntity customersEntity = new CustomersEntity();
        customersEntity.setFullName(customersDTO.getFullName());
        customersEntity.setEmail(customersDTO.getEmail());
        customersEntity.setPhoneNumber(customersDTO.getPhoneNumber());
        customersEntity.setNumber_id(customersDTO.getNumber_id());
        customersEntity.setAddress(customersDTO.getAddress());
        customersEntity.setStatus(1);
        CustomersEntity customersEntity1 = iCustomerRepository.save(customersEntity);
        return customersEntity1.getCustomerId();
    }

    @Override
    public CustomersDTO getById(Long id) {
        CustomersEntity customersEntity = iCustomerRepository.findById(id).get();
        CustomersDTO customersDTO = new CustomersDTO();
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
