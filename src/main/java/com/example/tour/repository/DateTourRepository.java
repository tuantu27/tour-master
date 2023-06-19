package com.example.tour.repository;

import com.example.tour.entity.DateTourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateTourRepository extends JpaRepository<DateTourEntity,Long> {
    DateTourEntity getByTour_TourIdAndStartDate(Long tourId,String startDate);
}
