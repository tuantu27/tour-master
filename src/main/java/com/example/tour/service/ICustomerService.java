package com.example.tour.service;

import com.example.tour.model.dto.CustomersDTO;

public interface ICustomerService {
    Long saveCusomer(CustomersDTO customersDTO);

    CustomersDTO getById(Long id);
}
