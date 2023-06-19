package com.example.tour.service;

import com.example.tour.entity.BookingEntity;
import com.example.tour.entity.CustomersEntity;
import com.example.tour.entity.ToursEntity;
import com.example.tour.model.dto.BookingDTO;
import com.example.tour.model.dto.CustomersDTO;
import com.example.tour.model.dto.DataDTO;
import com.example.tour.model.dto.ToursDTO;
import com.example.tour.repository.IBookingRepository;
import com.example.tour.repository.ICustomerRepository;
import com.example.tour.repository.ITourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService{
    @Autowired
    private IBookingRepository iBookingRepository;

    @Autowired
    ICustomerRepository iCustomerRepository;

    @Autowired
    ITourRepository iTourRepository;

    @Override
    public Long saveBooking(BookingDTO bookingDTO) {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBookingTime(bookingDTO.getBookingTime());
        bookingEntity.setStatus(1);
        bookingEntity.setNumberAdult(bookingDTO.getNumberAdult());
        bookingEntity.setStartDate(bookingDTO.getStartDate());
        bookingEntity.setNumberChildren(bookingDTO.getNumberChildren());
        bookingEntity.setNumberInfant(bookingDTO.getNumberInfant());
        bookingEntity.setTotalPrice(bookingDTO.getTotalPrice());
        bookingEntity.setPaymentMethod(bookingDTO.getPaymentMethod());
        bookingEntity.setTour(iTourRepository.findById(bookingDTO.getToursDTO().getTourId()).get());
        bookingEntity.setCustomer(iCustomerRepository.findById(bookingDTO.getCustomersDTO().getCustomerId()).get());
        BookingEntity bookingEntity1 = iBookingRepository.save(bookingEntity);
        return bookingEntity1.getBookingId();
    }

    @Override
    public BookingDTO getById(Long booking_id) {
        Optional<BookingEntity> bookingEntity = iBookingRepository.findById(booking_id);
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(bookingEntity.get().getBookingId());
        bookingDTO.setBookingTime(bookingEntity.get().getBookingTime());
        bookingDTO.setPaymentMethod(bookingEntity.get().getPaymentMethod());
        bookingDTO.setNumberAdult(bookingEntity.get().getNumberAdult());
        bookingDTO.setNumberChildren(bookingEntity.get().getNumberChildren());
        bookingDTO.setNumberInfant(bookingEntity.get().getNumberInfant());
        bookingDTO.setStatus(bookingEntity.get().getStatus());
        bookingDTO.setStartDate(bookingEntity.get().getStartDate());
        bookingDTO.setTotalPrice(bookingEntity.get().getTotalPrice());
        Optional<ToursEntity> toursEntity = iTourRepository.findById(bookingEntity.get().getTour().getTourId());
        ToursDTO toursDTO = toursEntity.get().toDto(toursEntity.get());
        bookingDTO.setToursDTO(toursDTO);
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getAllByAccountIdAndStatus(Long accountId,int status) {
        List<BookingEntity> list = iBookingRepository.getBookingEntitiesByTour_Account_AccountIdAndStatus(accountId,status);
        List<BookingDTO> list1 = new ArrayList<>();
        for(BookingEntity bookingEntity: list){
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setBookingId(bookingEntity.getBookingId());
            bookingDTO.setBookingTime(bookingEntity.getBookingTime());
            bookingDTO.setPaymentMethod(bookingEntity.getPaymentMethod());
            bookingDTO.setNumberAdult(bookingEntity.getNumberAdult());
            bookingDTO.setNumberChildren(bookingEntity.getNumberChildren());
            bookingDTO.setNumberInfant(bookingEntity.getNumberInfant());
            bookingDTO.setStatus(bookingEntity.getStatus());
            bookingDTO.setStartDate(bookingEntity.getStartDate());
            bookingDTO.setTotalPrice(bookingEntity.getTotalPrice());
            Optional<ToursEntity> toursEntity = iTourRepository.findById(bookingEntity.getTour().getTourId());
            ToursDTO toursDTO = toursEntity.get().toDto(toursEntity.get());
            Optional<CustomersEntity> customersEntity = iCustomerRepository.findById(bookingEntity.getCustomer().getCustomerId());
            CustomersDTO customersDTO = customersEntity.get().toDto(customersEntity.get());
            bookingDTO.setToursDTO(toursDTO);
            bookingDTO.setCustomersDTO(customersDTO);
            list1.add(bookingDTO);
        }
        return list1;
    }

    @Override
    public void deleteBooking(Long booking_id) {
        BookingEntity bookingEntity = iBookingRepository.findById(booking_id).get();
        bookingEntity.setStatus(0);
        iBookingRepository.save(bookingEntity);
    }

    @Override
    public List<DataDTO> getTotal_Month(Long accountId) {
        List<DataDTO> dataDTOList = iBookingRepository.getTotal_Month(accountId);
        return dataDTOList;
    }

    @Override
    public List<DataDTO> getTotal_People(Long accountId) {
        List<DataDTO> dataDTOList = iBookingRepository.getTotal_People(accountId);
        return dataDTOList;
    }
}
