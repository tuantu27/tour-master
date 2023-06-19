package com.example.tour.service;

import com.example.tour.model.dto.BookingDTO;
import com.example.tour.model.dto.DataDTO;

import java.util.List;

public interface IBookingService {
    Long saveBooking(BookingDTO bookingDTO);
    BookingDTO getById(Long booking_id);

    List<BookingDTO> getAllByAccountIdAndStatus(Long accountId,int status);
    void deleteBooking(Long booking_id);

    List<DataDTO> getTotal_Month(Long accountId);
    List<DataDTO> getTotal_People(Long accountId);
}
