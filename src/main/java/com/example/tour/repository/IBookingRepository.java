package com.example.tour.repository;

import com.example.tour.entity.BookingEntity;
import com.example.tour.model.dto.DataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBookingRepository extends JpaRepository<BookingEntity,Long> {

    List<BookingEntity> getBookingEntitiesByTour_Account_AccountIdAndStatus(Long accountId,int status);


    @Query("select new com.example.tour.model.dto.DataDTO( FUNCTION('MONTH',b.bookingTime),SUM(b.totalPrice)) from BookingEntity b where b.tour.account.accountId = ?1 GROUP BY FUNCTION('MONTH',b.bookingTime) ORDER BY FUNCTION('MONTH',b.bookingTime) ASC" )
    List<DataDTO> getTotal_Month(Long accountId);
    @Query("select new com.example.tour.model.dto.DataDTO( FUNCTION('MONTH',b.bookingTime),SUM(b.totalPrice),SUM(b.numberAdult + b.numberChildren + b.numberInfant)) from BookingEntity b where b.tour.account.accountId = ?1 GROUP BY FUNCTION('MONTH',b.bookingTime) ORDER BY FUNCTION('MONTH',b.bookingTime) ASC" )
    List<DataDTO> getTotal_People(Long accountId);

}
