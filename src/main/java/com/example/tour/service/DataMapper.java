package com.example.tour.service;

import com.example.tour.model.dto.BookingDTO;
import com.example.tour.model.dto.CustomersDTO;
import com.example.tour.model.dto.ToursDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.context.Context;
@Service
public class DataMapper {

    public Context setData(BookingDTO bookingDTO, CustomersDTO customersDTO, ToursDTO toursDTO) {

        Context context = new Context();

        Map<String, Object> data = new HashMap<>();

        data.put("customersDTO", customersDTO);
        data.put("bookingDTO", bookingDTO);
        data.put("toursDTO", toursDTO);

        context.setVariables(data);

        return context;
    }
}
